package fr.ele.services.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.ele.model.Entity;
import fr.ele.services.dao.GenericDao;

@Repository
public abstract class GenericDaoImpl<T extends Entity> implements GenericDao<T> {

    private final Class<T> clazz;

    @Autowired
    SessionFactory sessionFactory;

    public GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(T entity) {
        getCurrentSession().persist(entity);

    }

    public void update(T entity) {
        getCurrentSession().merge(entity);

    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public T getById(Long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
