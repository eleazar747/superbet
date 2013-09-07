package fr.ele.services.repositories.custom;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.mysema.query.jpa.JPQLQuery;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.QBookMakerSynchronization;
import fr.ele.model.ref.BookMaker;

public class BookMakerSynchronizationRespositoryCustomImpl extends
        QueryDslRepositorySupport implements
        BookMakerSynchronizationRespositoryCustom {

    public BookMakerSynchronizationRespositoryCustomImpl(Class<?> domainClass) {
        super(domainClass);
    }

    @Override
    public BookMakerSynchronization lastSync(BookMaker bookMaker) {
        QBookMakerSynchronization entity = QBookMakerSynchronization.bookMakerSynchronization;
        JPQLQuery query = from(entity);
        query.where(entity.bookMaker.eq(bookMaker));
        query.orderBy(entity.synchronizationDate.desc());
        query.limit(1);
        return query.singleResult(entity);
    }

}
