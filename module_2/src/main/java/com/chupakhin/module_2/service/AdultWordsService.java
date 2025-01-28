package com.chupakhin.module_2.service;

import com.chupakhin.module_2.topology.MessageTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class AdultWordsService {

    @Value("${kafka.topics.adult-words}")
    private String adultWordsTopic;

    @Value("${kafka.stores.adult-words}")
    private String adultWordsStoreName;

    @Value("${init-data.adult-word-marker}")
    private String adultWordMarker;

    private final MessageTopology messageTopology;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AdultWordsService(MessageTopology messageTopology, KafkaTemplate<String, String> kafkaTemplate) {
        this.messageTopology = messageTopology;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Set<String> getAdultWords() {
        Set<String> words = new HashSet<>();
        ReadOnlyKeyValueStore<String, String> adultWordsStore = messageTopology.getStreams().store(
                StoreQueryParameters.fromNameAndType(adultWordsStoreName, QueryableStoreTypes.keyValueStore())
        );
        try (KeyValueIterator<String, String> iterator = adultWordsStore.all()) {
            while (iterator.hasNext()) {
                words.add(iterator.next().key);
            }
        }
        return words;
    }

    public void addAdultWords(Set<String> adultWords) throws ExecutionException, InterruptedException {
        for (String word : adultWords) {
            kafkaTemplate.send(adultWordsTopic, word, adultWordMarker).get();
        }
    }

    public void removeAdultWords(Set<String> adultWords) throws ExecutionException, InterruptedException {
        for (String word : adultWords) {
            kafkaTemplate.send(adultWordsTopic, word, null).get();
        }
    }
}
