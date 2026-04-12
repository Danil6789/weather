package org.example.weatherviewer.forecast.location.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {
    private Long id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
