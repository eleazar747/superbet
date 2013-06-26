package fr.ele.services;

import fr.ele.model.util.Entity;

public interface GenericDao<T extends Entity> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T getById(Long id);
}
