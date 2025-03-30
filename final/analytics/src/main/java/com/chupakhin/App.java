package com.chupakhin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.spark.sql.functions.lit;

public class App {

    private static final String BOOTSTRAP_SERVERS = "kafka-1:9092, kafka-2:9093, kafka-3:9094";
    private static final String SEARCH_TOPIC = "search-topic";
    private static final String RECOMMENDATION_TOPIC = "recommendation-topic";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hadoop-spark-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(SEARCH_TOPIC));

        String hdfsUri = "hdfs://hadoop-namenode:9000";
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);

        try (FileSystem hdfs = FileSystem.get(new URI(hdfsUri), conf, "root")) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    String key = record.key();
                    System.out.println("Получен запрос: " + value + " от: " + key);

                    String hdfsFilePath = "/data/" + key;
                    Path path = new Path(hdfsFilePath);

                    try (FSDataOutputStream outputStream = hdfs.create(path, true)) {
                        outputStream.write(value.getBytes(StandardCharsets.UTF_8));
                    }
                    System.out.println("Сообщение записано в HDFS по пути: " + hdfsFilePath);
                    processWithSpark(hdfsUri, key, hdfsFilePath);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private static void processWithSpark(String hdfsUri, String key, String path) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("KafkaHdfsSparkConsumer");
        try (JavaSparkContext sc = new JavaSparkContext(sparkConf)) {
            JavaRDD<String> hdfsData = sc.textFile(hdfsUri + path);
            // Преобразуем JavaRDD в Dataset
            SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
            Dataset<String> dataSet = spark.createDataset(hdfsData.rdd(), Encoders.STRING()).as("search");
            dataSet.show();
            // Записываем в Kafka
            dataSet.withColumn("user", lit(key))
                    .toJSON()
                    .write()
                    .format("kafka")
                    .option("kafka.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("topic", RECOMMENDATION_TOPIC)

                    .save();

        }
    }
}
