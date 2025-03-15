package com.chupakhin.serde;

import com.chupakhin.dto.Model;
import com.chupakhin.dto.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class JsonModelDeserializer implements Deserializer<Model> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Model deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, Model.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
