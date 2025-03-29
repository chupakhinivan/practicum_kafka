package com.chupakhin.shopapi.controller;

import com.chupakhin.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${kafka.topics.products}")
    private String productsTopic;

    private final KafkaTemplate<String, Product> kafkaTemplate;

    public ProductController(KafkaTemplate<String, Product> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody List<Product> products) {
        try {
            for (Product product : products) {
                kafkaTemplate.send(productsTopic, product.getName(), product);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Message sent successfully");
    }
}



