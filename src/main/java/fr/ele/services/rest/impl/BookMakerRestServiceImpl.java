package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.BookMakerRestService;

@Transactional
@Service(BookMakerRestService.SERVER)
public class BookMakerRestServiceImpl extends
        AbstractRefRestServiceImpl<BookMaker> implements BookMakerRestService {

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    protected BookMakerRepository getRepository() {
        return bookMakerRepository;
    }

    @Override
    public Iterable<BookMaker> findAll() {
        return super.findAll();
    }

    @Override
    protected QBookMaker entityPath() {
        return QBookMaker.bookMaker;
    }

    @Override
    public BookMaker create(BookMaker bookmaker) {
        return super.create(bookmaker);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public List<BookMaker> insertCsv(Attachment file) {
        return insertCsv(file, BookMaker.class);
    }

    @Override
    public Iterable<BookMaker> search(BookmakerSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), search);
        return bookMakerRepository.findAll(queryBuilder.build());
    }

}
