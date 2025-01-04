package com.chupakhin;

import com.chupakhin.dto.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.common.config.TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private static final String BOOTSTRAP_SERVERS = "localhost:9094";
    private static final String TOPIC_NAME = "message-topic";
    private static final long PUSH_DELAY_MS = 1000;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        // Конфигурация продюсера – адрес сервера, сериализаторы для ключа и значения.
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ACKS_CONFIG, "all"); // Для синхронной репликации
        properties.put(RETRIES_CONFIG, 3); // Количество попыток при ошибке
        properties.put(MIN_IN_SYNC_REPLICAS_CONFIG, 2); // Минимальное количество синхронных реплик

        // Создание продюсера
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            int messageNo = 1;
            while (messageNo < 1000) {
                String key = "key-%s".formatted(messageNo % 3);
                // Каждое 25е сообщение не пройдет десериализацию
                String message =
                        messageNo % 25 == 0 ? "Failed deserialize" :
                        objectMapper.writeValueAsString(new UserMessage(messageNo, "Hi from user-" + messageNo));

                // Отправка сообщения
                producer.send(new ProducerRecord<>(TOPIC_NAME, key, message), (metadata, exception) -> {
                    if (exception != null) {
                        logger.error("Ошибка при отправке сообщения: {}", exception.getMessage());
                    } else {
                        logger.info("Сообщение отправлено: message={}, key={}, partition={}, offset={}",
                                message, key, metadata.partition(), metadata.offset());
                    }
                });
                messageNo++;
                sleep();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(PUSH_DELAY_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
