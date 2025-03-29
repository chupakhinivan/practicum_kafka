package com.chupakhin.productfilter.topology;

import com.chupakhin.productfilter.topology.processor.AdultProductsTopicProcessor;
import com.chupakhin.productfilter.topology.processor.ProductsTopicProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static org.apache.kafka.common.serialization.Serdes.StringSerde;

@Component
@Slf4j
public class ProductTopology {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final StreamsBuilder builder;
    private final ProductsTopicProcessor productsTopicProcessor;
    private final AdultProductsTopicProcessor adultProductsTopicProcessor;

    @Getter
    private KafkaStreams streams;

    public ProductTopology(StreamsBuilder builder,
                           ProductsTopicProcessor productsTopicProcessor,
                           AdultProductsTopicProcessor adultProductsTopicProcessor) {
        this.builder = builder;
        this.productsTopicProcessor = productsTopicProcessor;
        this.adultProductsTopicProcessor = adultProductsTopicProcessor;
    }

    @PostConstruct
    public void runStream() {

        adultProductsTopicProcessor.process();
        productsTopicProcessor.process();

        streams = new KafkaStreams(builder.build(), getProperties());

        streams.setStateListener(((newState, oldState) -> {
            if(KafkaStreams.State.RUNNING.equals(newState)) {
                log.info("Kafka Streams started!");
            }
        }));

        streams.cleanUp();
        streams.start();
    }

    private Properties getProperties() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "product-filter");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StringSerde.class);
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        return config;
    }

    @PreDestroy
    public void closeStream() {
        streams.close();
    }
}
