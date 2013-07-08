package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.Entity;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.rest.RefRestService;

public abstract class AbstractRefRestServiceImpl<T extends Entity> implements
        RefRestService<T> {

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Transactional(readOnly = true)
    public T get(long id) {
        return (T) getDao().getById(id);
    }

    protected abstract GenericDao getDao();
}
