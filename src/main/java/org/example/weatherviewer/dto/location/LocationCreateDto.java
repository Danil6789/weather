package org.example.weatherviewer.dto.location;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationCreateDto {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}