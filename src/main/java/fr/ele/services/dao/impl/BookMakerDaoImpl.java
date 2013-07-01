package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.impl.BookMakerImpl;
import fr.ele.model.ref.impl.QBookMakerImpl;
import fr.ele.services.dao.BookMakerDao;

@Repository
public class BookMakerDaoImpl extends GenericDaoImpl<BookMaker, QBookMakerImpl>
        implements BookMakerDao {

    public BookMakerDaoImpl() {
        super(BookMakerImpl.class, QBookMakerImpl.bookMakerImpl);
    }

    @Override
    public BookMaker findByCode(String code) {
        HibernateQuery query = new HibernateQuery(getCurrentSession());
        return query.from(entityQuery).where(entityQuery.code.eq(code))
                .uniqueResult(entityQuery);
    }

}
