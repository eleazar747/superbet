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
import fr.ele.dto.SportDto;
import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.model.search.SportSearch;
import fr.ele.services.repositories.SportRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.SportRestService;

@Transactional(readOnly = true)
@Service(SportRestService.SERVER)
public class SportRestServiceImpl extends
        AbstractRefRestServiceImpl<SportDto, Sport> implements SportRestService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    protected SportRepository getRepository() {
        return sportRepository;
    }

    @Override
    protected QSport entityPath() {
        return QSport.sport;
    }

    @Override
    @Timed
    public Iterable<SportDto> findAll() {
        return super.findAll();
    }

    @Override
    @Timed
    @Transactional
    public SportDto create(SportDto model) {
        return super.create(model);
    }

    @Override
    @Timed
    @Transactional
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    @Timed
    @Transactional
    public List<SportDto> insertCsv(Attachment file) {
        return insertCsv(file, Sport.class);
    }

    @Override
    @Timed
    public Iterable<SportDto> search(SportSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), search);
        return Translate.to(dtoClass()).fromEach(
                Lists.newArrayList(getRepository()
                        .findAll(queryBuilder.build())));
    }

    @Override
    protected Class<Sport> modelClass() {
        return Sport.class;
    }

    @Override
    protected Class<SportDto> dtoClass() {
        return SportDto.class;
    }

    @Override
    public SportDto findByCode(String code) {
        return super.findByCode(code);
    }

    @Override
    public SportDto get(long id) {
        return super.get(id);
    }

}
