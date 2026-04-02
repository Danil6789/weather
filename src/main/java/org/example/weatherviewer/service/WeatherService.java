package org.example.weatherviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.weatherviewer.dto.weather.GeocodingResponse;
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
public class WeatherService {

    //TODO: HttpClient надо сделать полем и бином в AppConfig чтоб можно было замокать его в тестах и использовать(Я не совсем в этом разобрался, оставил на потом)

    private ObjectMapper objectMapper;

    @Value("OPENWEATHER_API_KEY")
    private String apiKey;

    private String getUrlGeocoding(String name){
        return "http://api.openweathermap.org/geo/1.0/direct?q=" +
                name + "&l&appid=" + apiKey;
    }

    public List<GeocodingResponse> searchLocations(String name) throws IOException {
        try(HttpClient client = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getUrlGeocoding(name)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            GeocodingResponse[] responses = objectMapper.readValue(json, GeocodingResponse[].class);

            return Arrays.asList(responses);
        }catch(Exception e){
            throw new IOException("Ошибка");
        }
    }

    public void getWeather(BigDecimal lat, BigDecimal lon){
        //TODO: доделать логику
    }
}
