package org.example.weatherviewer.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(
        basePackages = "org.example.weatherviewer",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        }
)
@Configuration
@EnableScheduling //TODO: это нужно будет убрать если делать через ScheduleExecutorService
public class AppConfig {
}