package fr.ele.services.rest.impl;

import com.codiform.moo.curry.Translate;
import com.mysema.query.types.path.EntityPathBase;

import fr.ele.dto.HasIdAndCodeDto;
import fr.ele.model.HasCodeEntity;
import fr.ele.services.repositories.HasCodeRepository;

public abstract class AbstractRefRestServiceImpl<DTO extends HasIdAndCodeDto, MODEL extends HasCodeEntity>
        extends AbstractBaseRestService<DTO, MODEL> {

    protected DTO findByCode(String code) {
        LOGGER.debug("findByCode({})", code);
        MODEL model = ((HasCodeRepository<MODEL>) getRepository())
                .findByCode(code);
        return Translate.to(dtoClass()).from(model);
    }

    protected abstract <Q extends EntityPathBase<MODEL>> Q entityPath();
}
