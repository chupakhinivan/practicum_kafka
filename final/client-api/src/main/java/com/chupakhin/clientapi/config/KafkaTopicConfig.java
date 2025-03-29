package com.chupakhin.clientapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.search}")
    private String searchTopic;

    @Value("${kafka.topics.recommendation}")
    private String recommendationTopic;

    @Bean
    public NewTopic searchTopic() {
        return TopicBuilder.name(searchTopic).partitions(3).replicas(2).build();
    }

    @Bean
    public NewTopic recommendationTopic() {
        return TopicBuilder.name(recommendationTopic).partitions(3).replicas(2).build();
    }
}
