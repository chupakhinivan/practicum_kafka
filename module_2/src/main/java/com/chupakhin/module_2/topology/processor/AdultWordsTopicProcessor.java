package com.chupakhin.module_2.topology.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.serialization.Serdes.String;

@Component
@Slf4j
public class AdultWordsTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.adult-words}")
    private String adultWordsTopic;

    @Value("${kafka.stores.adult-words}")
    private String adultWordsStoreName;

    private final StreamsBuilder builder;

    public AdultWordsTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {

        // Создаем State Store цензурных слов
        builder.stream(adultWordsTopic, Consumed.with(String(), String()))
                .peek((word, marker) -> {
                    if (marker == null){
                        log.info("Tombstone! Слово '{}' удалено из запрещенных", word);
                    } else {
                        log.info("Слово '{}' теперь запрещено!", word);
                    }
                })
                .toTable(
                        Materialized .<String, String, KeyValueStore<Bytes, byte[]>>as(adultWordsStoreName)
                                .withKeySerde(String())
                                .withValueSerde(String())
                );
    }
}
