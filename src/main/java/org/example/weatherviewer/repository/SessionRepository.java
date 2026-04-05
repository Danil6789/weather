package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.example.weatherviewer.exception.DatabaseException;
import org.example.weatherviewer.exception.SessionAlreadyExistsException;
import org.example.weatherviewer.entity.Session;
import org.hibernate.HibernateException;
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
        try{
            entityManager.persist(session);
        }catch(ConstraintViolationException e){
            throw new SessionAlreadyExistsException("Такая сессия уже существует");
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд", e);
        }
    }

    public int deleteById(UUID sessionId) {
        try{
            return entityManager
                    .createQuery("DELETE FROM Session WHERE id = :id")
                    .setParameter("id", sessionId)
                    .executeUpdate();
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при удалении сессии", e);
        }
    }

    public Optional<Session> findById(UUID id){
        try{
            return Optional.ofNullable(entityManager.find(Session.class, id));
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при поиске сессии", e);
        }
    }

    public int deleteByExpiresAtBefore(LocalDateTime now){
        try{
            return entityManager
                    .createQuery("DELETE FROM Session WHERE expiresAt < :now")
                    .setParameter("now", now).executeUpdate();
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при удалении, когда истекла сессия", e);
        }

    }

    public void clearCache() {
        entityManager.flush();
        entityManager.clear();
    }
}