package com.chupakhin;

import com.chupakhin.dto.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.common.config.TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        Properties props = getProperties();

        // Конфигурация продюсера – адрес сервера, сериализаторы для ключа и значения.
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, props.getProperty("producer.bootstrap.servers"));
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ACKS_CONFIG, props.getProperty("producer.acks")); // Для синхронной репликации
        properties.put(RETRIES_CONFIG, props.getProperty("producer.retries")); // Количество попыток при ошибке
        properties.put(MIN_IN_SYNC_REPLICAS_CONFIG, props.getProperty("producer.min.insync.replicas")); // Минимальное количество синхронных реплик

        String topic = props.getProperty("producer.topic.name");
        long pushDelayMs = Long.parseLong(props.getProperty("producer.push.delay.ms"));
        long pushMessageCount = Long.parseLong(props.getProperty("producer.push.message.count"));

        // Создание продюсера
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            int messageNo = 1;
            while (messageNo < pushMessageCount) {
                String key = "key-%s".formatted(messageNo % 3);
                // Каждое 25е сообщение не пройдет десериализацию
                String message =
                        messageNo % 25 == 0 ? "Failed deserialize" :
                        objectMapper.writeValueAsString(new UserMessage(messageNo, "Hi from user-" + messageNo));

                // Отправка сообщения
                producer.send(new ProducerRecord<>(topic, key, message), (metadata, exception) -> {
                    if (exception != null) {
                        logger.error("Ошибка при отправке сообщения: {}", exception.getMessage());
                    } else {
                        logger.info("Сообщение отправлено: message={}, key={}, partition={}, offset={}",
                                message, key, metadata.partition(), metadata.offset());
                    }
                });
                messageNo++;
                sleep(pushDelayMs);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties() {
        try (InputStream input = Producer.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
