package com.chupakhin.clientapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchParam {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("product_name")
    private String productName;

    public SearchParam(String userName, String productName) {
        this.userName = userName;
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
