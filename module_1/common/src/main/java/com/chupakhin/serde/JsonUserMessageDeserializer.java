package com.chupakhin.serde;

import com.chupakhin.dto.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class JsonUserMessageDeserializer implements Deserializer<UserMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserMessage deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, UserMessage.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
