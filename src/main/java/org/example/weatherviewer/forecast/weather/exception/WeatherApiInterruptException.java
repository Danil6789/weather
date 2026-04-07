package org.example.weatherviewer.forecast.weather.exception;

public class WeatherApiInterruptException extends RuntimeException {
    public WeatherApiInterruptException(String message) {
        super(message);
    }
}
