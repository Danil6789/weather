package org.example.weatherviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.weatherviewer.config.TestAppConfig;
import org.example.weatherviewer.forecast.weather.dto.GeocodingResponse;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiInterruptException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiNetworkException;
import org.example.weatherviewer.forecast.weather.exception.WeatherApiResponseException;
import org.example.weatherviewer.forecast.weather.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@Transactional
@ContextConfiguration(classes = {TestAppConfig.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "openweather.api.key=test-key")
class WeatherServiceTest {
    @Autowired
    @Qualifier("mockHttpClient")
    private HttpClient httpClient;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Проверяет, что ответ от WeatherApi возвращается правильно")
    void searchLocations_shouldReturnLocations_whenApiReturnsValidResponse() throws Exception {
        HttpResponse<Object> mockResponse = mock(HttpResponse.class);

        String mockJsonResponse = """
            [
                {
                    "name": "London",
                    "country": "GB",
                    "lat": 51.5073,
                    "lon": -0.1276,
                    "state": "England"
                },
                {
                    "name": "Paris",
                    "country": "FR", 
                    "lat": 48.8566,
                    "lon": 2.3522
                }
            ]
        """;

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(mockResponse);

        List<GeocodingResponse> result = weatherService.searchLocations("London");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("London", result.get(0).getName());
        assertEquals("GB", result.get(0).getCountry());
        assertEquals("Paris", result.get(1).getName());
        assertEquals("FR", result.get(1).getCountry());
    }

    @Test
    @DisplayName("Симулируем IOException от того, что нет интернета и сервер не отвечает)")
    void searchLocations_shouldThrowNetworkException_whenConnectionRefused() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenThrow(new IOException("Connection refused"));

        assertThrows(WeatherApiNetworkException.class, () -> {
            weatherService.searchLocations("London");
        });
    }

    @Test
    @DisplayName("Симулируем InterruptedException от того, что поток прерван")
    void searchLocations_shouldThrowInterruptException_whenThreadInterrupted() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenThrow(new InterruptedException("Thread interrupted"));

        assertThrows(WeatherApiInterruptException.class, () -> {
            weatherService.searchLocations("London");
        });
    }

    @Test
    @DisplayName("Проверка что выбрасывается WeatherApiResponseException если ответ пришел со статусом отличным от 200")
    void searchLocations_shouldThrowWeatherApiResponseException_whenApiReturns404() throws Exception {
        HttpResponse<Object> mockResponse = (HttpResponse<Object>) mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(401);
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(mockResponse);

        assertThrows(WeatherApiResponseException.class, () -> {
            weatherService.searchLocations("London");
        });
    }
}
