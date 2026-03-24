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

    private String name;  // название города

    private Main main;    // основная информация

    private List<Weather> weather;  // описание погоды

    private Wind wind;     // ветер

    @Data
    @NoArgsConstructor
    public static class Main {
        private BigDecimal temp;      // температура
        @JsonProperty("feels_like")
        private BigDecimal feelsLike; // ощущается как
        private Integer humidity;      // влажность
        private Integer pressure;      // давление
    }

    @Data
    @NoArgsConstructor
    public static class Weather {
        private String main;        // группа (Rain, Clear, Clouds)
        private String description; // описание
        private String icon;        // иконка
    }

    @Data
    @NoArgsConstructor
    public static class Wind {
        private BigDecimal speed;   // скорость ветра
    }
}