package fr.ele.services.dao;

import java.util.List;

import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.Entity;

public interface GenericDao<T extends Entity, Q extends EntityPathBase<? extends T>> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T getById(Long id);

    List<T> findAll();
}
