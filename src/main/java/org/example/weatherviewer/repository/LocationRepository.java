package org.example.weatherviewer.repository;

import org.example.weatherviewer.entity.Location;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LocationRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Location> findLocationsByUserId(Long userId){
        return sessionFactory.getCurrentSession().createQuery("FROM Location WHERE user_id =:userId", Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
