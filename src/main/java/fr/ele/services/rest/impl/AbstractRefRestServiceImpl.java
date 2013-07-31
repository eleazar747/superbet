package fr.ele.services.rest.impl;

import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.HasCodeEntity;
import fr.ele.services.repositories.HasCodeRepository;

public abstract class AbstractRefRestServiceImpl<T extends HasCodeEntity>
        extends AbstractBaseRestService<T> {

    @Transactional(readOnly = true)
    public T findByCode(String code) {
        LOGGER.debug("findByCode({})", code);
        return ((HasCodeRepository<T>) getRepository()).findByCode(code);
    }

    protected abstract <Q extends EntityPathBase<T>> Q entityPath();
}
