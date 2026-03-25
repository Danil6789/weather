package org.example.weatherviewer.repository;

import org.example.weatherviewer.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user){
        sessionFactory.getCurrentSession().persist(user);


    }

    public Optional<User> findByLogin(String login){
        return sessionFactory.getCurrentSession().
                createQuery("From User WHERE login = :login",User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
    }

    public void update(User user){
        sessionFactory.getCurrentSession().merge(user);
    }
}
