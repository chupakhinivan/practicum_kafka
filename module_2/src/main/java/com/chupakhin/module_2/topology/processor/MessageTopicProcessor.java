package com.chupakhin.module_2.topology.processor;

import com.chupakhin.module_2.dto.UserCensoredMessage;
import com.chupakhin.module_2.dto.UserMessage;
import com.chupakhin.module_2.processor.CensoringMessageProcessor;
import com.chupakhin.module_2.processor.FilterBlockedUserProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.serialization.Serdes.String;

@Component
@Slf4j
public class MessageTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.messages}")
    private String messageTopic;

    @Value("${kafka.stores.blocked-users}")
    private String blockedUsersStoreName;

    @Value("${kafka.stores.adult-words}")
    private String adultWordsStoreName;

    @Value("${kafka.topics.dirty-messages}")
    private String dirtyMessageTopic;

    private final StreamsBuilder builder;

    public MessageTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {

        // Создаем поток из message-topic
        KStream<String, UserMessage> messageStream = builder.stream(messageTopic, Consumed.with(String(), new JsonSerde<>(UserMessage.class)))
                .peek((key, value) -> log.info("'{}' отправил сообщение для '{}': {}",
                        value.getSenderUser(),
                        value.getRecipientUser(),
                        value.getMessage()));

        // Фильтрация сообщений по отправителю
        KStream<String, UserMessage> withoutBlockMessagesStream = messageStream
                .process(() -> new FilterBlockedUserProcessor(blockedUsersStoreName), blockedUsersStoreName);

        // Цензурирование сообщений
        KStream<String, UserCensoredMessage> censoredMessageStream = withoutBlockMessagesStream
                .process(() -> new CensoringMessageProcessor(adultWordsStoreName), adultWordsStoreName);

        // Отправляем сообщения, имеющие нарушения в топик dirty-messages-topic
        censoredMessageStream
                .filter((senderId, userCensoredMessage) -> userCensoredMessage.isHasViolation())
                .to(dirtyMessageTopic, Produced.with(String(), new JsonSerde<>(UserCensoredMessage.class)));

        // Информируем о получении сообщения, прошедшего фильтр по отправителю и цензурирование
        censoredMessageStream
                .peek((senderUser, censoredMessage) -> log.info("'{}' получил сообщение от '{}': {}",
                        censoredMessage.getRecipientUser(),
                        censoredMessage.getSenderUser(),
                        censoredMessage.getMessage()));
    }
}
