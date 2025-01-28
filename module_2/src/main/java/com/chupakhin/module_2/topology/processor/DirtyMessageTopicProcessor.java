package com.chupakhin.module_2.topology.processor;

import com.chupakhin.module_2.dto.UserCensoredMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.serialization.Serdes.String;

@Component
@Slf4j
public class DirtyMessageTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.dirty-messages}")
    private String dirtyMessageTopic;

    @Value("${acceptable-violation-count}")
    private Integer acceptableViolationCount;

    @Value("${kafka.topics.block-user-event}")
    private String blockUserEventTopic;

    private final StreamsBuilder builder;

    public DirtyMessageTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {

        // Поток, обрабатывающий сообщения с нарушениями
        builder.stream(dirtyMessageTopic, Consumed.with(String(), new JsonSerde<>(UserCensoredMessage.class)))
                .groupBy((senderId, userMessage) -> userMessage.getSenderUser() + "->" + userMessage.getRecipientUser())
                .count()
                .toStream()
                .peek(((key, count) -> log.info("'{}' количество нарушений: {}", key, count)))
                .filter((key, value) -> value > acceptableViolationCount - 1)
                .map((key, value) -> {
                    String[] senderToRecipient = key.split("->");
                    return KeyValue.pair(senderToRecipient[1], senderToRecipient[0]);
                })
                .to(blockUserEventTopic);
    }
}
