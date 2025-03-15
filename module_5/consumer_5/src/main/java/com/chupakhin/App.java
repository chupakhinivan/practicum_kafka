package com.chupakhin;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import main.java.com.chupakhin.User;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class App {
    public static void main(String[] args) {

        String HOST = "rc1a-jl69nc45v9ec7sbf.mdb.yandexcloud.net:9091,rc1b-qpk8n48l9cfktnsp.mdb.yandexcloud.net:9091,rc1d-loi78vj6a7rhiov0.mdb.yandexcloud.net:9091";
        String TOPIC = "my-topic";
        String USER = "client";
        String PASS = "client-password";
        String TS_FILE = "/etc/security/ssl";
        String TS_PASS = "password";
        String schemaRegistryUrl = "https://rc1a-jl69nc45v9ec7sbf.mdb.yandexcloud.net:443";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, USER, PASS);
        String GROUP = "demo";

        String deserializer = StringDeserializer.class.getName();
        Properties props = new Properties();
        props.put("bootstrap.servers", HOST);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("group.id", GROUP);
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", KafkaJsonSchemaDeserializer.class.getName());
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-512");
        props.put("sasl.jaas.config", jaasCfg);
        props.put("ssl.truststore.location", TS_FILE);
        props.put("ssl.truststore.password", TS_PASS);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put( SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO" );
        props.put( "schema.registry.basic.auth.user.info",USER+":"+PASS );

        Consumer<String, User> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(new String[] {TOPIC}));

        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Caught shutdown signal, closing consumer...");
            consumer.wakeup();
            latch.countDown();
        }));

        try {
            while (true) {
                ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, User> record : records) {
                    System.out.printf("Message on %s:%n%s%n", record.topic(), record.value());
                }
            }
        } catch (WakeupException e) {
            // Expected during shutdown
        } finally {
            consumer.close();
            System.out.println("Consumer closed");
            latch.countDown();
        }
    }
}
