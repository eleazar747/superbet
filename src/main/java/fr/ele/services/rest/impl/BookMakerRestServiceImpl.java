package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codahale.metrics.annotation.Timed;
import com.codiform.moo.curry.Translate;
import com.google.common.collect.Lists;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.dto.BookmakerDto;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.QBookMaker;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.BookMakerRestService;

@Transactional(readOnly = true)
@Service(BookMakerRestService.SERVER)
public class BookMakerRestServiceImpl extends
        AbstractRefRestServiceImpl<BookmakerDto, BookMaker> implements
        BookMakerRestService {

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    protected BookMakerRepository getRepository() {
        return bookMakerRepository;
    }

    @Override
    @Timed
    public Iterable<BookmakerDto> findAll() {
        return super.findAll();
    }

    @Override
    protected QBookMaker entityPath() {
        return QBookMaker.bookMaker;
    }

    @Override
    @Transactional
    @Timed
    public BookmakerDto create(BookmakerDto bookmaker) {
        return super.create(bookmaker);
    }

    @Override
    @Transactional
    @Timed
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @Timed
    public List<BookmakerDto> insertCsv(Attachment file) {
        return insertCsv(file, BookMaker.class);
    }

    @Override
    @Timed
    public Iterable<BookmakerDto> search(BookmakerSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), search);
        Iterable<BookMaker> models = bookMakerRepository.findAll(queryBuilder
                .build());
        return Translate.to(dtoClass()).fromEach(Lists.newArrayList(models));
    }

    @Override
    @Timed
    public BookmakerDto findByCode(String code) {
        return super.findByCode(code);
    }

    @Override
    @Timed
    public BookmakerDto get(long id) {
        return super.get(id);
    }

    @Override
    protected Class<BookMaker> modelClass() {
        return BookMaker.class;
    }

    @Override
    protected Class<BookmakerDto> dtoClass() {
        return BookmakerDto.class;
    }

}
