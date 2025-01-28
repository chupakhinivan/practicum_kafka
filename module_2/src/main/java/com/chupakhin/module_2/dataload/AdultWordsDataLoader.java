package com.chupakhin.module_2.dataload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AdultWordsDataLoader implements ApplicationRunner {

    @Value("${init-data.adult-words}")
    private List<String> initAdultWords;

    @Value("${kafka.topics.adult-words}")
    private String adultWordsTopic;

    @Value("${init-data.adult-word-marker}")
    private String adultWordMarker;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AdultWordsDataLoader(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (String word : initAdultWords) {
            kafkaTemplate.send(adultWordsTopic, word, adultWordMarker);
        }
    }
}
