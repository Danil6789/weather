package org.example.weatherviewer.repository;

import org.example.weatherviewer.entity.User;
import org.example.weatherviewer.exception.DatabaseException;
import org.example.weatherviewer.exception.UserAlreadyExistsException;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends CrudRepository<User> {

    public UserRepository(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public void save(User user) {
        try {
            sessionFactory.getCurrentSession().persist(user);
        }catch (ConstraintViolationException e){
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
        }
        catch (HibernateException e){
            throw new DatabaseException("Ошибка в бд", e);
        }
    }

    public Optional<User> findByLogin(String login) {
        return sessionFactory.getCurrentSession().
                createQuery("From User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().merge(user); //TODO: Посмотреть почему он никогда не используется может он не нужен или я зыбал его вписать куда-то
    }

    public List<User> findAll(){
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class)
                .getResultList();
    }
}
