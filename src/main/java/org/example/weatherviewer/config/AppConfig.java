package org.example.weatherviewer.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
        basePackages = "org.example.weatherviewer",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.weatherviewer.controller.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.weatherviewer.handler.*")
        }
)
@Configuration
@EnableScheduling //TODO: это нужно будет убрать если делать через ScheduleExecutorService
public class AppConfig {
}