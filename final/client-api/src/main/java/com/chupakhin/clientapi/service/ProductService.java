package com.chupakhin.clientapi.service;

import com.chupakhin.clientapi.dto.SearchParam;
import com.chupakhin.dto.Product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${store.products.path}")
    private String productsPath;

    @Value("${kafka.topics.search}")
    private String searchTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public List<Product> getProductsByName(SearchParam searchParam) throws IOException {
        kafkaTemplate.send(searchTopic, searchParam.getUserName(), searchParam.getProductName());
        return readProductsFromFile(productsPath).stream().filter(product -> searchParam.getProductName().equals(product.getName())).toList();
    }

    public List<Product> getProductsByTag(String tag) throws IOException {
        return readProductsFromFile(productsPath).stream().filter(product -> {
            List<String> tags = product.getTags();
            for (String productTag : tags) {
                for (String userTagPart : tag.split(" ")) {
                    if (productTag.toLowerCase().contains(userTagPart.toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }).toList();
    }

    private List<Product> readProductsFromFile(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {

                    Product product = objectMapper.readValue(line, Product.class);
                    products.add(product);
                } catch (JsonParseException e) {
                    System.err.println("Ошибка парсинга строки: " + line);
                }
            }
        }

        return products;
    }
}
