package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import org.example.weatherviewer.entity.Location;
import org.example.weatherviewer.exception.DatabaseException;
import org.example.weatherviewer.exception.LocationAlreadyExistsException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepository extends CrudRepository<Location> {

    public LocationRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Location> findLocationsByUserId(Long userId){
        try{
            return entityManager.createQuery("FROM Location WHERE user.id =:userId", Location.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при поиске локации", e);
        }
    }

    @Override
    public void save(Location location){
        try{
            entityManager.persist(location);
        }catch(ConstraintViolationException e){
            throw new LocationAlreadyExistsException("Такая локация уже отслеживается");
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при добавлении локации", e);
        }
    }

    public int deleteByIdAndUserId(Long id, Long userId){
        try{
            return entityManager.createQuery("DELETE FROM Location WHERE id=:id AND user.id=:userId")
                    .setParameter("id", id).setParameter("userId", userId).executeUpdate();
        }catch(HibernateException e){
            throw new DatabaseException("Ошибка в бд при удалении локации", e);
        }
    }
}