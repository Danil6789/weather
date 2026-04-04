package org.example.weatherviewer.repository;

import org.hibernate.SessionFactory;
import org.example.weatherviewer.entity.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository extends CrudRepository<Session>{

    public SessionRepository(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public void save(Session session){
        sessionFactory.getCurrentSession().persist(session);
    }

    public int deleteById(UUID sessionId) {
        return sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Session WHERE id = :id")
                .setParameter("id", sessionId)
                .executeUpdate();
    }

    public Optional<Session> findById(UUID id){
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Session.class, id));
    }

    public int deleteByExpiresAtBefore(LocalDateTime now){
        return sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Session WHERE expires_at < :now")
                .setParameter("now", now).executeUpdate();
    }

    public void clearCash() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}