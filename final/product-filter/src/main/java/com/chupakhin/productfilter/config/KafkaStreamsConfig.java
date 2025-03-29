package com.chupakhin.productfilter.config;

import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamsConfig {

    @Bean()
    public StreamsBuilder builder() {
        return new StreamsBuilder();
    }
}
