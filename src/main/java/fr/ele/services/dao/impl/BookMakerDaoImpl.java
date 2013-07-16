package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;
import fr.ele.services.dao.BookMakerDao;

@Repository
public class BookMakerDaoImpl extends GenericDaoImpl<BookMaker, QBookMaker>
        implements BookMakerDao {

    public BookMakerDaoImpl() {
        super(BookMaker.class, QBookMaker.bookMaker);
    }

    @Override
    public BookMaker findByCode(String code) {
        JPAQuery query = new JPAQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
