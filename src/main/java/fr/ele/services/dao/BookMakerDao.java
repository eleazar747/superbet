package fr.ele.services.dao;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;

public interface BookMakerDao extends GenericDao<BookMaker, QBookMaker> {
    BookMaker findByCode(String code);
}
