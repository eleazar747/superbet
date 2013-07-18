package fr.ele.services.rest.impl;

import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.HasCodeEntity;
import fr.ele.services.repositories.HasCodeRepository;
import fr.ele.services.rest.RefRestService;

public abstract class AbstractRefRestServiceImpl<T extends HasCodeEntity>
        extends AbstractBaseRestService<T> implements RefRestService<T> {

    @Override
    @Transactional(readOnly = true)
    public T findByCode(String code) {
        LOGGER.debug("findByCode({})", code);
        return ((HasCodeRepository<T>) getRepository()).findByCode(code);
    }

    protected abstract <Q extends EntityPathBase<T>> Q entityPath();
}
