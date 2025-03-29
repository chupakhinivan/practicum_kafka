package com.chupakhin.clientapi.service;

import com.chupakhin.clientapi.recommendation.Recommendation;
import com.chupakhin.dto.Product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Value("${store.recommendations.path}")
    private String recommendationPath;

    public List<Product> getRecommendationByUserName(String userName) throws IOException {
        Optional<Recommendation> recommendation = readRecommendationsFromFile(recommendationPath, userName);
        if (recommendation.isPresent()) {
            return productService.getProductsByTag(recommendation.get().getValue());
        } else {
            return new ArrayList<>();
        }
    }

    private Optional<Recommendation> readRecommendationsFromFile(String filePath, String userName) throws IOException {
        Recommendation recommendation = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {

                    if (!line.isBlank() && !line.isEmpty()){
                        Recommendation currentRecommendation = objectMapper.readValue(line, Recommendation.class);
                        if (userName.equals(currentRecommendation.getUser())){
                            recommendation = currentRecommendation;
                        }
                    }
                } catch (JsonParseException e) {
                    System.err.println("Ошибка парсинга строки: " + line);
                }
            }
        }

        return Optional.of(recommendation);
    }
}
