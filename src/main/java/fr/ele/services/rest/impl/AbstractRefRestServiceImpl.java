package fr.ele.services.rest.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.HasCodeEntity;
import fr.ele.queries.Queries;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.rest.RefRestService;

public abstract class AbstractRefRestServiceImpl<T extends HasCodeEntity>
        implements RefRestService<T> {

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

    @Override
    @Transactional(readOnly = true)
    public T findByCode(String code) {
        return getRepository().findOne(Queries.findByCode(entityPath(), code));
    }

    protected abstract SuperBetRepository<T> getRepository();

    protected abstract <Q extends EntityPathBase<T>> Q entityPath();
}
