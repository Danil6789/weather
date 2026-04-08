package org.example.weatherviewer.forecast;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.forecast.location.dto.LocationDto;
import org.example.weatherviewer.forecast.weather.dto.WeatherResponse;
import org.example.weatherviewer.forecast.location.LocationService;
import org.example.weatherviewer.forecast.weather.WeatherService;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
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
                .map(locationDto -> {
                    WeatherResponse weather = weatherService.getWeather(locationDto.getLatitude(), locationDto.getLongitude());
                    weather.setName(locationDto.getName());
                    return new ForecastDto(
                            locationDto.getId(),
                            locationDto.getLongitude().setScale(4, RoundingMode.HALF_UP),
                            locationDto.getLatitude().setScale(4, RoundingMode.HALF_UP),
                            weather);
                })
                .collect(Collectors.toList());
    }
}