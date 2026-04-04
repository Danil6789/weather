package org.example.weatherviewer.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.weatherviewer.dto.weather.WeatherResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDto {
    private Long locationId;
    private WeatherResponse weather;
}
