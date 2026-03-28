package org.example.weatherviewer.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(
        basePackages = "org.example.weatherviewer",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        }
)
@Configuration
public class AppConfig {
}