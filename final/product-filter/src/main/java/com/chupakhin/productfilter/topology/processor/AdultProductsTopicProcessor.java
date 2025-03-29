package com.chupakhin.productfilter.topology.processor;

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
public class AdultProductsTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.adult-products}")
    private String adultProductsTopic;

    @Value("${kafka.stores.adult-products}")
    private String adultProductsStoreName;

    private final StreamsBuilder builder;

    public AdultProductsTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {

        // Создаем State Store запрещенных товаров
        builder.stream(adultProductsTopic, Consumed.with(String(), String()))
                .peek((word, marker) -> {
                    if (marker == null){
                        log.info("Tombstone! Товар '{}' удален из запрещенных", word);
                    } else {
                        log.info("Товар '{}' теперь запрещен!", word);
                    }
                })
                .toTable(
                        Materialized .<String, String, KeyValueStore<Bytes, byte[]>>as(adultProductsStoreName)
                                .withKeySerde(String())
                                .withValueSerde(String())
                );
    }
}
