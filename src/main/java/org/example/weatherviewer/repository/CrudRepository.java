package org.example.weatherviewer.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public abstract class CrudRepository<T> {
    protected final SessionFactory sessionFactory; //TODO: подумать мб нужно сделать через entityManager

    abstract public void save(T entity);
}
