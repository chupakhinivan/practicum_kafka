package com.chupakhin.module_2.topology;

import com.chupakhin.module_2.topology.processor.AdultWordsTopicProcessor;
import com.chupakhin.module_2.topology.processor.BlockUserEventTopicProcessor;
import com.chupakhin.module_2.topology.processor.DirtyMessageTopicProcessor;
import com.chupakhin.module_2.topology.processor.MessageTopicProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static org.apache.kafka.common.serialization.Serdes.StringSerde;

@Component
@Slf4j
public class MessageTopology {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final StreamsBuilder builder;
    private final MessageTopicProcessor messageTopicProcessor;
    private final BlockUserEventTopicProcessor blockUserEventTopicProcessor;
    private final AdultWordsTopicProcessor adultWordsTopicProcessor;
    private final DirtyMessageTopicProcessor dirtyMessageTopicProcessor;

    @Getter
    private KafkaStreams streams;

    public MessageTopology(StreamsBuilder builder,
                           MessageTopicProcessor messageTopicProcessor,
                           BlockUserEventTopicProcessor blockUserEventTopicProcessor,
                           AdultWordsTopicProcessor adultWordsTopicProcessor,
                           DirtyMessageTopicProcessor dirtyMessageTopicProcessor) {
        this.builder = builder;
        this.messageTopicProcessor = messageTopicProcessor;
        this.blockUserEventTopicProcessor = blockUserEventTopicProcessor;
        this.adultWordsTopicProcessor = adultWordsTopicProcessor;
        this.dirtyMessageTopicProcessor = dirtyMessageTopicProcessor;
    }

    @PostConstruct
    public void runStream() {

        blockUserEventTopicProcessor.process();
        adultWordsTopicProcessor.process();
        dirtyMessageTopicProcessor.process();
        messageTopicProcessor.process();

        streams = new KafkaStreams(builder.build(), getProperties());

        streams.setStateListener(((newState, oldState) -> {
            if(KafkaStreams.State.RUNNING.equals(newState)) {
                log.info("Kafka Streams started!");
            }
        }));

        streams.cleanUp();
        streams.start();
    }

    private Properties getProperties() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StringSerde.class);
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        return config;
    }

    @PreDestroy
    public void closeStream() {
        streams.close();
    }
}
