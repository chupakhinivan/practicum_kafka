package com.chupakhin.clientapi.controller;

import com.chupakhin.clientapi.service.RecommendationService;
import com.chupakhin.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userName}")
    public ResponseEntity<Object> getProductsByName(@PathVariable("userName") String userName) {
        try {
            List<Product> products = recommendationService.getRecommendationByUserName(userName);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}



