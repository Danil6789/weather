package org.example.weatherviewer.exception.weather;

public class WeatherApiNetworkException extends RuntimeException {
    public WeatherApiNetworkException(String message) {
        super(message);
    }
}
