package org.example.weatherviewer.dto.location;

import lombok.Data;
import org.example.weatherviewer.entity.User;

import java.math.BigDecimal;

@Data
public class LocationDto {
    private String name;
    private Long userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
