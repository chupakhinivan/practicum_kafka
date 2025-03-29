package com.chupakhin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Product {
    @JsonProperty("product_id")
    private String productId;
    private String name;
    private String description;
    private Price price;
    private String category;
    private String brand;
    private Stock stock;
    private String sku;
    private List<String> tags;
    private List<Image> images;
    private Specifications specifications;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_id")
    private LocalDateTime updatedAt;
    private String index;
    @JsonProperty("store_id")
    private String storeId;
}
