package org.example.weatherviewer.repository;

import lombok.RequiredArgsConstructor;
import org.example.weatherviewer.entity.Location;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository {
    private final SessionFactory sessionFactory;

    public List<Location> findLocationsByUserId(Long userId){
        return sessionFactory.getCurrentSession().createQuery("FROM Location WHERE user.id =:userId", Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void save(Location location){
        sessionFactory.getCurrentSession().persist(location);
    }
}
