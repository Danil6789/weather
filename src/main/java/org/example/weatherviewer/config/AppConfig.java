package org.example.weatherviewer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("org.example.weatherviewer")
@Import({DatabaseConfig.class, FlywayConfig.class, SecurityConfig.class})
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
