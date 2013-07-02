package fr.ele.services.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.Entity;
import fr.ele.services.dao.GenericDao;

@Repository
public abstract class GenericDaoImpl<T extends Entity, Q extends EntityPathBase<? extends T>>
        implements GenericDao<T, Q> {

    private final Class<? extends T> clazz;

    protected final Q entityQuery;

    @Autowired
    SessionFactory sessionFactory;

    public GenericDaoImpl(Class<? extends T> clazz, Q entityQuery) {
        this.clazz = clazz;
        this.entityQuery = entityQuery;
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

    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        return (List<T>) query.from(entityQuery).list(entityQuery);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Class<? extends T> handledClass() {
        return clazz;
    }
}
