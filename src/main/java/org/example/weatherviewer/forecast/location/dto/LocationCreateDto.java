package org.example.weatherviewer.forecast.location.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationCreateDto {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}