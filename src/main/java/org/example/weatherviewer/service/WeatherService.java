package org.example.weatherviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.weatherviewer.dto.openWeather.GeocodingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private ObjectMapper objectMapper;

    @Value("OPENWEATHER_API_KEY")
    private String apiKey;

    private String getUrlGeocoding(String name){
        return "http://api.openweathermap.org/geo/1.0/direct?q=" +
                name + "&limit=1&l&appid=" + apiKey;
    }


    private GeocodingResponse searchLocations(String name) throws IOException, InterruptedException {
        try(HttpClient client = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getUrlGeocoding(name)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            return objectMapper.readValue(json, GeocodingResponse.class);
        }catch(Exception e){
            throw new IOException("Ошибка");
        }
    }

    public void getWeather(BigDecimal lat, BigDecimal lon){
        //TODO: доделать логику
    }
}
