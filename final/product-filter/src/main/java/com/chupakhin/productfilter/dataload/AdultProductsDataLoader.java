package com.chupakhin.productfilter.dataload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AdultProductsDataLoader implements ApplicationRunner {

    @Value("${init-data.adult-products}")
    private List<String> initAdultProducts;

    @Value("${kafka.topics.adult-products}")
    private String adultProductsTopic;

    @Value("${init-data.adult-product-marker}")
    private String adultProductMarker;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AdultProductsDataLoader(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (String product : initAdultProducts) {
            kafkaTemplate.send(adultProductsTopic, product, adultProductMarker);
        }
    }
}
