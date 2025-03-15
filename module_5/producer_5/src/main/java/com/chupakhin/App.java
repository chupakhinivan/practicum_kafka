package com.chupakhin;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import main.java.com.chupakhin.SchemaRegistryHelper;
import main.java.com.chupakhin.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

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

        String serializer = StringSerializer.class.getName();
        Properties props = new Properties();
        props.put("bootstrap.servers", HOST);
        props.put("acks", "all");
        props.put("key.serializer", serializer);
        props.put("value.serializer", KafkaJsonSchemaSerializer.class.getName());
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-512");
        props.put("sasl.jaas.config", jaasCfg);
        props.put("ssl.truststore.location", TS_FILE);
        props.put("ssl.truststore.password", TS_PASS);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("auto.register.schemas", true);
        props.put("use.latest.version", true);
        props.put( SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO" );
        props.put( "schema.registry.basic.auth.user.info",USER+":"+PASS );

        SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 10);
        Producer<String, User> producer = new KafkaProducer<>(props);

        // Register schema
        String userSchema = "{ \"$schema\": \"http://json-schema.org/draft-07/schema#\", \"title\": \"User\", \"type\": \"object\", \"properties\": { \"name\": { \"type\": \"string\" }, \"favoriteNumber\": { \"type\": \"integer\" }, \"favoriteColor\": { \"type\": \"string\" } }, \"required\": [\"name\", \"favoriteNumber\", \"favoriteColor\"] }";
        SchemaRegistryHelper.registerSchema(schemaRegistryClient, TOPIC, userSchema);

        User user = new User("First user", 42, "blue");

        ProducerRecord<String, User> record = new ProducerRecord<>(TOPIC, user.getName(), user);

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Delivery failed: " + exception.getMessage());
                exception.printStackTrace(System.err);
            } else {
                System.out.printf("Delivered message to topic %s [%d] at offset %d%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });

        producer.close();
    }
}
