package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.example.weatherviewer.entity.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository extends CrudRepository<Session>{

    public SessionRepository(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public void save(Session session){
        entityManager.persist(session);
    }

    public int deleteById(UUID sessionId) {
        return entityManager
                .createQuery("DELETE FROM Session WHERE id = :id")
                .setParameter("id", sessionId)
                .executeUpdate();
    }

    public Optional<Session> findById(UUID id){
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }

    public int deleteByExpiresAtBefore(LocalDateTime now){
        return entityManager
                .createQuery("DELETE FROM Session WHERE expires_at < :now")
                .setParameter("now", now).executeUpdate();
    }

    public void clearCache() {
        entityManager.flush();
        entityManager.clear();
    }
}