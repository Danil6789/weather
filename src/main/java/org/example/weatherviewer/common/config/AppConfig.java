package org.example.weatherviewer.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.http.HttpClient;

@ComponentScan(
        basePackages = "org.example.weatherviewer"
//        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.weatherviewer.controller.*"),
//                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.weatherviewer.handler.*")
//        }
)
@Configuration
@EnableScheduling
public class AppConfig {
        @Bean
        public ObjectMapper objectMapper() {
                return new ObjectMapper();
        }

        @Bean(destroyMethod = "close")
        @Profile("!test")
        public HttpClient httpClient(){
                return HttpClient.newHttpClient();
        }
}