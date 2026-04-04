package org.example.weatherviewer.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.example.weatherviewer.entity.Session;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository { //TODO: Сделать общий класс допустим CrudRepository в котором будет добавлено sessionFactory/entityManager и какие-то общие методы по типу T save(T entity);

    @Autowired
    private SessionFactory sessionFactory; //TODO: подумать мб нужно сделать через entityManager

    public void save(Session session){
        sessionFactory.getCurrentSession().persist(session);
    }

    public int deleteById(UUID sessionId) {
        int result = sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Session WHERE id = :id")
                .setParameter("id", sessionId)
                .executeUpdate();

        return result;
    }

    public Optional<Session> findById(UUID id){
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Session.class, id));
    }

    public int deleteByExpiresAtBefore(LocalDateTime now){
        return sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Session WHERE expires_at < :now")
                .setParameter("now", now).executeUpdate();
    }

    public Optional<Session> findBySessionId(UUID sessionId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Session s WHERE s.id = :sessionId", Session.class)
                .setParameter("sessionId", sessionId)
                .uniqueResultOptional();
    }


    public void clearCash() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}