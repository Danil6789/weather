package org.example.weatherviewer.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class CrudRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    public CrudRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract public void save(T entity);
}