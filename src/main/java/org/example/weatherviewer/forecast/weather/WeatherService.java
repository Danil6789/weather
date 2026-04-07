package org.example.weatherviewer.forecast.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.forecast.weather.dto.GeocodingResponse;
import org.example.weatherviewer.forecast.weather.dto.WeatherResponse;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiInterruptException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiNetworkException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${openweather.api.key}")
    private String apiKey;

    private String getUrlGeocoding(String name){
        return "http://api.openweathermap.org/geo/1.0/direct?q=" +
                name + "&limit=5&appid=" + apiKey;
    }

    private String getUrlCurrentWeather(BigDecimal lat, BigDecimal lon){
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;
    }

    public List<GeocodingResponse> searchLocations(String name) {
        String url = getUrlGeocoding(name);
        GeocodingResponse[] responses = executeRequest(url, GeocodingResponse[].class);
        return Arrays.asList(responses);
    }

    public WeatherResponse getWeather(BigDecimal lat, BigDecimal lon) {
        String url = getUrlCurrentWeather(lat, lon);
        return executeRequest(url, WeatherResponse.class);
    }

    private <T> T executeRequest(String url, Class<T> responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherApiResponseException("Error request api");
            }

            return objectMapper.readValue(response.body(), responseType);
        } catch (IOException e) {
            throw new WeatherApiNetworkException("Connection error to OpenWeather API");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherApiInterruptException("Request was interrupted");
        }
    }
}