package org.example.weatherviewer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.entity.Session;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.SessionNotFoundException;
import org.example.weatherviewer.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

        if(line == 0){
            throw new SessionNotFoundException("Session not found by delete");
        }
    }

    @Transactional(readOnly = true)
    public User getUserBySessionId(UUID sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));
    }

    @Transactional
    @Scheduled(fixedRateString = "#{${session.timeout.hours:2}*3600000}")
    public void cleanupExpiredSession(){
        LocalDateTime now = LocalDateTime.now();
        int line = sessionRepository.deleteByExpiresAtBefore(now);
    }

}