package org.example.weatherviewer.exception.weather;

public class WeatherApiResponseException extends RuntimeException {
    public WeatherApiResponseException(String message) {
        super(message);
    }
}
