package org.example.weatherviewer.exception.weather;

public class WeatherApiInterruptException extends RuntimeException {
    public WeatherApiInterruptException(String message) {
        super(message);
    }
}
