package com.chupakhin.productfilter.controller;

import com.chupakhin.productfilter.service.AdultProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/adult-products")
@AllArgsConstructor
public class AdultProductsController {

    private final AdultProductsService adultProductsService;

    @GetMapping
    public ResponseEntity<Set<String>> getAdultProducts() {
        Set<String> adultProducts = adultProductsService.getAdultProducts();
        return ResponseEntity.ok(adultProducts);
    }

    @PostMapping
    public ResponseEntity<String> addAdultProducts(@RequestBody Set<String> adultProducts) {
        try {
            adultProductsService.addAdultProducts(adultProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Adult products added");
    }

    @DeleteMapping
    public ResponseEntity<String> removeAdultProducts(@RequestBody Set<String> adultProducts) {
        try {
            adultProductsService.removeAdultProducts(adultProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Adult products removed");
    }
}



