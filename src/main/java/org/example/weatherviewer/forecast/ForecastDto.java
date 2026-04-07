package org.example.weatherviewer.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.weatherviewer.forecast.weather.dto.WeatherResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDto {
    private Long locationId;
    private WeatherResponse weather;
}
