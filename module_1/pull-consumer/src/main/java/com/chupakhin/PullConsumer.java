package com.chupakhin;

import com.chupakhin.deserializer.JsonUserMessageDeserializer;
import com.chupakhin.dto.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class PullConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PullConsumer.class);

    private static final String BOOTSTRAP_SERVERS = "localhost:9094";
    private static final String TOPIC_NAME = "message-topic";
    private static final int POLL_DELAY_MS = 10_000;
    private static final int FETCH_MIN_BYTES = 10 * 1024;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        // Настройка консьюмера
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);  // Адрес брокера Kafka
        props.put(GROUP_ID_CONFIG, "pull-consumer-group");        // Уникальный идентификатор группы
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonUserMessageDeserializer.class);
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");        // Начало чтения с самого начала
        props.put(ENABLE_AUTO_COMMIT_CONFIG, false);           // Автоматический коммит смещений
        props.put(SESSION_TIMEOUT_MS_CONFIG, 6000);           // Время ожидания активности от консьюмера
        props.put(FETCH_MIN_BYTES_CONFIG, FETCH_MIN_BYTES);  // Минимальный объём данных (в байтах), который консьюмер должен получить за один запрос к брокеру Kafka
        props.put(FETCH_MAX_WAIT_MS_CONFIG, POLL_DELAY_MS); // Время в мс, которое консьюмер ждет объем, указанный в FETCH_MIN_BYTES_CONFIG


        // Создание консьюмера
        try (KafkaConsumer<String, UserMessage> consumer = new KafkaConsumer<>(props)) {
            // Подписка на топик
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            // Чтение сообщений в бесконечном цикле
            while (true) {
                try {
                    ConsumerRecords<String, UserMessage> records = consumer.poll(Duration.ofMillis(POLL_DELAY_MS));  // Получение сообщений
                    for (ConsumerRecord<String, UserMessage> record : records) {
                            logger.info("message: {}, key: {}, partition: {}, offset: {}",
                                    objectMapper.writeValueAsString(record.value()), record.key(), record.partition(), record.offset());
                    }
                    consumer.commitSync();
                } catch (RecordDeserializationException e) {
                    logger.error("Ошибка десериализации! partition: {}, offset: {}", e.topicPartition().partition(), e.offset());
                    // Пропустить проблемное сообщение и продолжить
                    consumer.seek(e.topicPartition(), e.offset() + 1);
                }
            }
        }
    }
}
