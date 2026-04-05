package org.example.weatherviewer.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private BigDecimal temp;
        @JsonProperty("feels_like")
        private BigDecimal feelsLike;
        private Integer humidity;

        @JsonProperty("feels_like")
        public void setFeelsLike(BigDecimal feelsLike) {
            this.feelsLike = convertKelvinToCelsius(feelsLike);
        }

        @JsonProperty("temp")
        public void setTemp(BigDecimal temp) {
            this.temp = convertKelvinToCelsius(temp);
        }

        private BigDecimal convertKelvinToCelsius(BigDecimal kelvin) {
            if (kelvin == null) return null;
            return kelvin.subtract(new BigDecimal("273.15"))
                    .setScale(1, RoundingMode.HALF_UP);
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        private BigDecimal speed;
    }
}