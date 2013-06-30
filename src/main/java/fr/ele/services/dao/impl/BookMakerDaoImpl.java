package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.impl.QBookMakerImpl;
import fr.ele.services.dao.BookMakerDao;

@Repository
public class BookMakerDaoImpl extends GenericDaoImpl<BookMaker> implements BookMakerDao {

	public BookMakerDaoImpl() {
		super(BookMaker.class);
	}

	@Override
	public BookMaker findByCode(String code) {
		HibernateQuery query = new HibernateQuery(getCurrentSession());
		QBookMakerImpl qBookMaker = QBookMakerImpl.bookMakerImpl;
        return query.from(qBookMaker).where(qBookMaker.code.eq(code)).uniqueResult(qBookMaker);
	}

}
