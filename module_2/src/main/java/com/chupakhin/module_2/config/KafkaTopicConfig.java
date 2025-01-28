package com.chupakhin.module_2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.messages}")
    private String messageTopic;

    @Value("${kafka.topics.dirty-messages}")
    private String dirtyMessageTopic;

    @Value("${kafka.topics.block-user-event}")
    private String blockUserEventTopic;

    @Value("${kafka.topics.adult-words}")
    private String adultWordsTopic;

    @Bean
    public NewTopic messagesTopic() {
        return TopicBuilder.name(messageTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic dirtyMessagesTopic() {
        return TopicBuilder.name(dirtyMessageTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic blockUserEventTopic() {
        return TopicBuilder.name(blockUserEventTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic adultWordsTopic() {
        return TopicBuilder.name(adultWordsTopic).partitions(1).replicas(1).compact().build();
    }
}
