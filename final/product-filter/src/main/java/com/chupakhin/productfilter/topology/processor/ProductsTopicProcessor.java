package com.chupakhin.productfilter.topology.processor;

import com.chupakhin.dto.Product;
import com.chupakhin.productfilter.processor.FilterAdultProductProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.serialization.Serdes.String;

@Component
@Slf4j
public class ProductsTopicProcessor implements TopicProcessor {

    @Value("${kafka.topics.source-products}")
    private String productsTopic;

    @Value("${kafka.topics.filter-products}")
    private String filterProductsTopic;

    @Value("${kafka.stores.adult-products}")
    private String adultProductsStoreName;

    private final StreamsBuilder builder;

    public ProductsTopicProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void process() {

        // Создаем поток из product-topic
        KStream<String, Product> productStream = builder.stream(productsTopic, Consumed.with(String(), new JsonSerde<>(Product.class)))
                .peek((key, value) -> log.info("Поступил товар: {}", value.getName()));

        // Фильтрация продуктов
        KStream<String, Product> filteredProductsStream = productStream
                .process(() -> new FilterAdultProductProcessor(adultProductsStoreName), adultProductsStoreName);

        // Отправляем товары, прошедшие фильтрацию в топик filter-products-topic
        filteredProductsStream
                .to(filterProductsTopic, Produced.with(String(), new JsonSerde<>(Product.class)));

        filteredProductsStream
                .peek((key, value) -> log.info("Товар '{}' добавлен", value.getName()));
    }
}
