package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import org.example.weatherviewer.entity.Location;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepository extends CrudRepository<Location> {

    public LocationRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Location> findLocationsByUserId(Long userId){
        return entityManager.createQuery("FROM Location WHERE user.id =:userId", Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void save(Location location){
        entityManager.persist(location);
    }

    public int deleteByIdAndUserId(Long id, Long userId){
        return entityManager.createQuery("DELETE FROM Location WHERE id=:id AND user.id=:userId")
                .setParameter("id", id).setParameter("userId", userId).executeUpdate();
    }
}