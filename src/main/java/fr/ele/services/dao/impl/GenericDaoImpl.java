package fr.ele.services.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.SuperBetEntity;
import fr.ele.services.dao.GenericDao;

@Repository
public abstract class GenericDaoImpl<T extends SuperBetEntity, Q extends EntityPathBase<? extends T>>
        implements GenericDao<T, Q> {

    private final Class<? extends T> clazz;

    protected final Q entityQuery;

    @Autowired
    EntityManagerFactory sessionFactory;

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
        getCurrentSession().remove(entity);
    }

    public T getById(Long id) {
        return getCurrentSession().find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        JPAQuery query = new JPAQuery(getCurrentSession());
        return (List<T>) query.from(entityQuery).list(entityQuery.getRoot());
    }

    protected final EntityManager getCurrentSession() {
        return sessionFactory.createEntityManager();
    }

    @Override
    public Class<? extends T> handledClass() {
        return clazz;
    }
}
