package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.BookMaker;
import fr.ele.services.dao.BookMakerDao;
import fr.ele.services.rest.BookMakerRestService;

@Transactional
@Service(BookMakerRestService.SERVER)
public class BookMakerRestServiceImpl implements BookMakerRestService {

    @Autowired
    private BookMakerDao bookMakerDao;

    @Transactional(readOnly = true)
    public List<BookMaker> findAll() {
        return bookMakerDao.findAll();
    }

    @Transactional(readOnly = true)
    public BookMaker get(long id) {
        return bookMakerDao.getById(id);
    }

    @Transactional(readOnly = true)
    public BookMaker findByCode(String code) {
        return bookMakerDao.findByCode(code);
    }

}
