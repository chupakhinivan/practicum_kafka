package com.chupakhin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Specifications {

    private String weight;
    private String dimensions;
    @JsonProperty("battery_life")
    private String batteryLife;
    @JsonProperty("water_resistance")
    private String waterResistance;
}
