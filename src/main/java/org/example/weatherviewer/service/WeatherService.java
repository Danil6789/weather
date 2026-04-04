package org.example.weatherviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.dto.weather.GeocodingResponse;
import org.example.weatherviewer.dto.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    //TODO: HttpClient надо сделать полем и бином в AppConfig чтоб можно было замокать его в тестах и использовать(Я не совсем в этом разобрался, оставил на потом)

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
        System.out.println("In searchLocations apikey:" + apiKey);
        try(HttpClient client = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getUrlGeocoding(name)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            GeocodingResponse[] responses = objectMapper.readValue(json, GeocodingResponse[].class);

            return Arrays.asList(responses);
        }catch(Exception e){
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    public WeatherResponse getWeather(BigDecimal lat, BigDecimal lon){
        try(HttpClient client = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getUrlCurrentWeather(lat, lon)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            return objectMapper.readValue(json, WeatherResponse.class);
        }catch(Exception e){
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
}
