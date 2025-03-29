package com.chupakhin.productfilter.service;

import com.chupakhin.productfilter.topology.ProductTopology;
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
public class AdultProductsService {

    @Value("${kafka.topics.adult-products}")
    private String adultProductsTopic;

    @Value("${kafka.stores.adult-products}")
    private String adultProductsStoreName;

    @Value("${init-data.adult-product-marker}")
    private String adultProductMarker;

    private final ProductTopology productTopology;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AdultProductsService(ProductTopology productTopology, KafkaTemplate<String, String> kafkaTemplate) {
        this.productTopology = productTopology;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Set<String> getAdultProducts() {
        Set<String> products = new HashSet<>();
        ReadOnlyKeyValueStore<String, String> adultProductsStore = productTopology.getStreams().store(
                StoreQueryParameters.fromNameAndType(adultProductsStoreName, QueryableStoreTypes.keyValueStore())
        );
        try (KeyValueIterator<String, String> iterator = adultProductsStore.all()) {
            while (iterator.hasNext()) {
                products.add(iterator.next().key);
            }
        }
        return products;
    }

    public void addAdultProducts(Set<String> adultProducts) throws ExecutionException, InterruptedException {
        for (String product : adultProducts) {
            kafkaTemplate.send(adultProductsTopic, product, adultProductMarker).get();
        }
    }

    public void removeAdultProducts(Set<String> adultProducts) throws ExecutionException, InterruptedException {
        for (String product : adultProducts) {
            kafkaTemplate.send(adultProductsTopic, product, null).get();
        }
    }
}
