package org.example.weatherviewer.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.weatherviewer.dto.weather.WeatherResponse;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDto {
    private BigDecimal latitude;
    private BigDecimal longitude;

    private WeatherResponse weather;
}
