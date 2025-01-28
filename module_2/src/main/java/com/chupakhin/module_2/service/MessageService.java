package com.chupakhin.module_2.service;

import com.chupakhin.module_2.dto.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class MessageService {

    @Value("${kafka.topics.messages}")
    private String messagesTopic;

    private final KafkaTemplate<String, UserMessage> kafkaTemplate;

    public MessageService(KafkaTemplate<String, UserMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserMessage userMessage) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(messagesTopic, userMessage.getSenderUser(), userMessage).get();
    }
}
