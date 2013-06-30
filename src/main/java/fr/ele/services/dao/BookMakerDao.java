package fr.ele.services.dao;

import fr.ele.model.ref.BookMaker;

public interface BookMakerDao extends GenericDao<BookMaker> {
	BookMaker findByCode(String code);
}
