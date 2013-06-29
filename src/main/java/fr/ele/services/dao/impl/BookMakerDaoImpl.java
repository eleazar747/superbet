package fr.ele.services.dao.impl;

import fr.ele.model.ref.BookMarker;
import fr.ele.services.dao.BookMakerDao;

public class BookMakerDaoImpl extends GenericDaoImpl<BookMarker> implements BookMakerDao {

	public BookMakerDaoImpl() {
		super(BookMarker.class);
	}

}
