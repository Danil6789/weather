package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.DatabaseException;
import org.example.weatherviewer.exception.UserAlreadyExistsException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends CrudRepository<User> {

    public UserRepository(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public void save(User user) {
        try {
            entityManager.persist(user);
        }catch (ConstraintViolationException e){
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
        }
        catch (HibernateException e){
            throw new DatabaseException("Ошибка в бд", e);
        }
    }

    public Optional<User> findByLogin(String login) {
        return entityManager.
                createQuery("From User WHERE login = :login", User.class)
                .setParameter("login", login)
                .getResultList()
                .stream()
                .findFirst();
    }

    public void update(User user) {
        entityManager.merge(user); //TODO: Посмотреть почему он никогда не используется может он не нужен или я зыбал его вписать куда-то
    }

    public List<User> findAll(){
        return entityManager.createQuery("FROM User", User.class)
                .getResultList();
    }
}
