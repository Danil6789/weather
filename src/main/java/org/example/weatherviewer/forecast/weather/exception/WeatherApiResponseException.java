package org.example.weatherviewer.forecast.weather.exception;

public class WeatherApiResponseException extends RuntimeException {
    public WeatherApiResponseException(String message) {
        super(message);
    }
}
