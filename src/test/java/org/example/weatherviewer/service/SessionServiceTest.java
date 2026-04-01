package org.example.weatherviewer.service;

import org.example.weatherviewer.config.TestAppConfig;
import org.example.weatherviewer.dto.auth.UserRegisterDto;
import org.example.weatherviewer.entity.Session;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.SessionNotFoundException;
import org.example.weatherviewer.repository.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@ContextConfiguration(classes = {TestAppConfig.class})
@ExtendWith(SpringExtension.class)
class SessionServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("Проверяем, что createSession создает сессию с правильным expiresAt")
    void createSession_createsSessionWithCorrectExpiry() {
        UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
        User user = userService.createUser(userDto);

        Session session = sessionService.createSession(user);

        assertNotNull(session);
        assertNotNull(session.getId());
        assertEquals(user.getId(), session.getUser().getId());
        assertTrue(session.getExpiresAt().isAfter(LocalDateTime.now()));
    }


    @Test
    @DisplayName("Проверяем, что после истечении сессии сама сессия удаляется")
    void getUserBySessionId_expiredSession_throwsException() {
        try {
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            User user = userService.createUser(userDto);
            Session session = sessionService.createSession(user);
            UUID sessionId = session.getId();

            Field expiresAtField = Session.class.getDeclaredField("expiresAt");
            expiresAtField.setAccessible(true);
            expiresAtField.set(session, LocalDateTime.now().minusHours(1));

            sessionRepository.save(session);

            assertThrows(SessionNotFoundException.class, () -> {
                sessionService.getUserBySessionId(sessionId);
            });

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Проверяем, что getUserBySessionId выбрасывает исключение, если сессия не найдена")
    void getUserBySessionId_sessionNotFound_throwsException() {
        UUID nonExistentSessionId = UUID.randomUUID();

        assertThrows(SessionNotFoundException.class, () -> {
            sessionService.getUserBySessionId(nonExistentSessionId);
        });
    }

    @Test
    @DisplayName("Проверяем, что getUserBySessionId продлевает сессию, если до истечения осталось меньше slidingWindow")
    void getUserBySessionId_nearExpirySession_extended() {
        try {
            UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
            User user = userService.createUser(userDto);
            Session session = sessionService.createSession(user);
            UUID sessionId = session.getId();

            Field expiresAtField = Session.class.getDeclaredField("expiresAt");
            expiresAtField.setAccessible(true);
            expiresAtField.set(session, LocalDateTime.now().plusMinutes(15));
            sessionRepository.save(session);

            LocalDateTime originalExpiresAt = session.getExpiresAt();
            sessionService.getUserBySessionId(sessionId);

            Session updatedSession = sessionRepository.findById(sessionId).get();
            assertTrue(updatedSession.getExpiresAt().isAfter(originalExpiresAt));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Проверяем, что deleteSession удаляет сессию")
    void deleteSession_deletesSession() {
        UserRegisterDto userDto = new UserRegisterDto("spider-man", "peter-parker", "peter-parker");
        User user = userService.createUser(userDto);
        Session session = sessionService.createSession(user);
        UUID sessionId = session.getId();

        assertTrue(sessionRepository.findById(sessionId).isPresent());

        sessionService.deleteSession(sessionId);
        sessionRepository.clearCash();

        assertFalse(sessionRepository.findById(sessionId).isPresent());
    }

}
