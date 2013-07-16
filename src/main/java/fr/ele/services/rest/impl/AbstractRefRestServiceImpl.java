package fr.ele.services.rest.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.SuperBetEntity;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.rest.RefRestService;

public abstract class AbstractRefRestServiceImpl<T extends SuperBetEntity> implements
        RefRestService<T> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Transactional(readOnly = true)
    public List<T> findAll() {
        LOGGER.debug("findAll");
        return getDao().findAll();
    }

    @Transactional(readOnly = true)
    public T get(long id) {
        LOGGER.debug("getById(id={})", id);
        return (T) getDao().getById(id);
    }

    protected abstract GenericDao getDao();
}
