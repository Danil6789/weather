package org.example.weatherviewer.service.forecast;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.forecast.ForecastDto;
import org.example.weatherviewer.dto.location.LocationDto;
import org.example.weatherviewer.dto.weather.WeatherResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForecastService {
    private final WeatherService weatherService;
    private final LocationService locationService;

    public List<ForecastDto> getUserForecasts(Long userId) {
        List<LocationDto> locations = locationService.getUserLocations(userId);

        return locations.stream()
                .sorted(Comparator.comparing(LocationDto::getId).reversed())
                .map(location -> {
                    WeatherResponse weather = weatherService.getWeather(location.getLatitude(), location.getLongitude());
                    return new ForecastDto(location.getId(), weather);
                })
                .collect(Collectors.toList());
    }
}