package org.example.weatherviewer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.entity.Session;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.SessionNotFoundException;
import org.example.weatherviewer.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionService {

    @Value("${session.timeout.hours:2}")
    private int sessionTimeoutHours;
    private final SessionRepository sessionRepository;

    @Transactional
    public Session createSession(User user){
        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now().plusHours(sessionTimeoutHours));
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    public void deleteSession(UUID sessionId) {
        int line = sessionRepository.deleteById(sessionId);
        //TODO: мб нужно проверку сколько удалилось строк сделать
    }

    @Transactional(readOnly = true)
    public User getUserBySessionId(UUID sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));
    }

    //TODO: нужно сделать метод планировщик с @Schedule для автоматическогого удаления зомби-сессии
}