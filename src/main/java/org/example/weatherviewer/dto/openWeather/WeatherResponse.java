package org.example.weatherviewer.dto.openWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private String name;

    private Main main;

    private List<Weather> weather;

    private Wind wind;

    @Data
    @NoArgsConstructor
    public static class Main {
        private BigDecimal temp;
        @JsonProperty("feels_like")
        private BigDecimal feelsLike;
        private Integer humidity;
        private Integer pressure;
    }

    @Data
    @NoArgsConstructor
    public static class Weather {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @NoArgsConstructor
    public static class Wind {
        private BigDecimal speed;
    }
}