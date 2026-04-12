package org.example.weatherviewer.common.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSource dataSource;

    @Bean
    @Profile("!test")
    public Flyway flyway(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        return flyway;
    }
}