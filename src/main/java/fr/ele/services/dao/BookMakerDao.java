package fr.ele.services.dao;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.impl.QBookMakerImpl;

public interface BookMakerDao extends GenericDao<BookMaker, QBookMakerImpl> {
    BookMaker findByCode(String code);
}
