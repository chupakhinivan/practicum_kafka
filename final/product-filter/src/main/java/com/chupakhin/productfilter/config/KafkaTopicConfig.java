package com.chupakhin.productfilter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.filter-products}")
    private String filterProductsTopic;

    @Value("${kafka.topics.adult-products}")
    private String adultProductsTopic;


    @Bean
    public NewTopic filterProductsTopic() {
        return TopicBuilder.name(filterProductsTopic).partitions(3).replicas(2).build();
    }

    @Bean
    public NewTopic adultProductsTopic() {
        return TopicBuilder.name(adultProductsTopic).partitions(3).replicas(2).compact().build();
    }
}
