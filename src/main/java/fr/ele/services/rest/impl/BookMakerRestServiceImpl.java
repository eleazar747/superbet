package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.BookMaker;
import fr.ele.services.dao.BookMakerDao;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.rest.BookMakerRestService;

@Transactional
@Service(BookMakerRestService.SERVER)
public class BookMakerRestServiceImpl extends
        AbstractRefRestServiceImpl<BookMaker> implements BookMakerRestService {

    @Autowired
    private BookMakerDao bookMakerDao;

    @Transactional(readOnly = true)
    public BookMaker findByCode(String code) {
        return bookMakerDao.findByCode(code);
    }

    @Override
    protected GenericDao getDao() {
        return bookMakerDao;
    }

}
