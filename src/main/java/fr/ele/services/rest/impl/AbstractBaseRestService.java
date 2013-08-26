package fr.ele.services.rest.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.SuperBetEntity;
import fr.ele.services.repositories.SuperBetRepository;

public abstract class AbstractBaseRestService<T extends SuperBetEntity> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Transactional(readOnly = true)
    public List<T> findAll() {
        LOGGER.debug("findAll");
        return getRepository().findAll();
    }

    @Transactional(readOnly = true)
    public T get(long id) {
        LOGGER.debug("getById(id={})", id);
        return getRepository().findOne(id);
    }

    @Transactional
    protected T create(T model) {
        getRepository().save(model);
        return model;
    }

    @Transactional
    protected void delete(long id) {
        getRepository().delete(id);
    }

    protected abstract SuperBetRepository<T> getRepository();

}
