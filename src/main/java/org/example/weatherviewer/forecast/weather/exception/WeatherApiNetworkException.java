package org.example.weatherviewer.forecast.weather.exception;

public class WeatherApiNetworkException extends RuntimeException {
    public WeatherApiNetworkException(String message) {
        super(message);
    }
}
