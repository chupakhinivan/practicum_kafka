package com.chupakhin.shopapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.products}")
    private String productsTopic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final String user = "admin";
    private final String pass = "admin-secret";

    @Bean
    public NewTopic messagesTopic() {
        return TopicBuilder.name(productsTopic).partitions(3).replicas(2).build();
    }

}
