package org.example.weatherviewer.exception.weather;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
