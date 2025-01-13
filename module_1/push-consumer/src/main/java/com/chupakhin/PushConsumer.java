package com.chupakhin;

import com.chupakhin.dto.UserMessage;
import com.chupakhin.serde.JsonUserMessageDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class PushConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PushConsumer.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {

        Properties props = getProperties();

        String topic = props.getProperty("push-consumer.topic.name");
        long pollDelayMs = Long.parseLong(props.getProperty("push-consumer.poll.delay.ms"));

        // Настройка консьюмера
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, props.getProperty("push-consumer.bootstrap.servers"));  // Адрес брокера Kafka
        properties.put(GROUP_ID_CONFIG, props.getProperty("push-consumer.group.id"));        // Уникальный идентификатор группы
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonUserMessageDeserializer.class);
        properties.put(AUTO_OFFSET_RESET_CONFIG, props.getProperty("push-consumer.auto.offset.reset"));        // Начало чтения с самого начала
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, true);           // Автоматический коммит смещений
        properties.put(SESSION_TIMEOUT_MS_CONFIG, props.getProperty("push-consumer.session.timeout.ms"));           // Время ожидания активности от консьюмера

        // Создание консьюмера
        try (KafkaConsumer<String, UserMessage> consumer = new KafkaConsumer<>(properties)) {
            // Подписка на топик
            consumer.subscribe(Collections.singletonList(topic));

            // Чтение сообщений в бесконечном цикле
            while (true) {
                try {
                    ConsumerRecords<String, UserMessage> records = consumer.poll(Duration.ofMillis(pollDelayMs));  // Получение сообщений
                    for (ConsumerRecord<String, UserMessage> record : records) {
                        logger.info("message: {}, key: {}, partition: {}, offset: {}",
                                objectMapper.writeValueAsString(record.value()), record.key(), record.partition(), record.offset());
                    }
                } catch (RecordDeserializationException e) {
                    logger.error("Ошибка десериализации! partition: {}, offset: {}", e.topicPartition().partition(), e.offset());
                    // Пропустить проблемное сообщение и продолжить
                    consumer.seek(e.topicPartition(), e.offset() + 1);
                }

            }
        }
    }

    private static Properties getProperties() {
        try (InputStream input = PushConsumer.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
