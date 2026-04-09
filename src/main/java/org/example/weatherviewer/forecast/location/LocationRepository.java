package org.example.weatherviewer.forecast.location;

import jakarta.persistence.EntityManager;
import org.example.weatherviewer.common.DatabaseException;
import org.example.weatherviewer.forecast.location.exception.LocationAlreadyExistsException;
import org.example.weatherviewer.common.CrudRepository;
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
            return entityManager.createQuery(
                            "SELECT DISTINCT l FROM Location l " +
                                    "JOIN FETCH l.user " +
                                    "WHERE l.user.id = :userId " +
                                    "ORDER BY l.id DESC", Location.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch(HibernateException e){
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