package fr.ele.queries;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.QHasCodeEntity;

public class Queries {

    private Queries() {

    }

    public static final <E extends HasCodeEntity, T extends EntityPathBase<E>> Predicate findByCode(
            T path, String code) {
        QHasCodeEntity hasCodeEntity = new QHasCodeEntity(path.getMetadata());
        return hasCodeEntity.code.eq(code);
    }
}
