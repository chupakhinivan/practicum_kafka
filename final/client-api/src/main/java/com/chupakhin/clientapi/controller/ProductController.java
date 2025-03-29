package com.chupakhin.clientapi.controller;

import com.chupakhin.clientapi.dto.SearchParam;
import com.chupakhin.clientapi.service.ProductService;
import com.chupakhin.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<Object> getProductsByName(@RequestParam("userName") String userName, @RequestParam("productName") String productName) {
        try {
            List<Product> products = productService.getProductsByName(new SearchParam(userName, productName));
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}



