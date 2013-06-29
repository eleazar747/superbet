package fr.ele.services.dao.impl;

import org.springframework.stereotype.Repository;

import fr.ele.model.ref.BookMaker;
import fr.ele.services.dao.BookMakerDao;

@Repository
public class BookMakerDaoImpl extends GenericDaoImpl<BookMaker> implements BookMakerDao {

	public BookMakerDaoImpl() {
		super(BookMaker.class);
	}

}
