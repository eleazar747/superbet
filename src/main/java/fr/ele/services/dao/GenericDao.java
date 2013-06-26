package fr.ele.services.dao;

import fr.ele.model.Entity;

public interface GenericDao<T extends Entity> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T getById(Long id);
}
