package com.chupakhin.module_2.topology.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.apache.kafka.common.serialization.Serdes.ListSerde;
import static org.apache.kafka.common.serialization.Serdes.String;

@Component
@Slf4j
public class BlockUserEventTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.block-user-event}")
    private String blockUserEventTopic;

    @Value("${kafka.stores.blocked-users}")
    private String blockedUsersStoreName;

    private final StreamsBuilder builder;

    public BlockUserEventTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {
        // Создаем поток из block-user-event-topic
        // Аггрегируем, группируем по ключу и записываем в blocked-users-store
        // В итоге blocked-users-store имеет в качестве ключа recipientId, в качестве значения список заблокированных для него senderIds
        builder.stream(blockUserEventTopic, Consumed.with(String(), String()))
                .peek((key, value) -> log.info("Сообщения от '{}' для '{}' будут заблокированы!", value, key))
                .groupByKey()
                .aggregate(
                        ArrayList::new,
                        (recipientId, senderId, blockedUsers) -> {
                            blockedUsers.add(senderId);
                            return blockedUsers;
                        },
                        Materialized .<String, List<String>, KeyValueStore<Bytes, byte[]>>as(blockedUsersStoreName)
                                .withKeySerde(String())
                                .withValueSerde(ListSerde(ArrayList.class, String()))
                );
    }
}
