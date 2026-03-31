package org.example.weatherviewer.service;

import org.example.weatherviewer.config.TestAppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@ContextConfiguration(classes = {TestAppConfig.class})
@ExtendWith(SpringExtension.class)
class SessionServiceTest {

    @Test
    @DisplayName("Проверяем, что после истечении сессии пользование недоступно")
    void

}
