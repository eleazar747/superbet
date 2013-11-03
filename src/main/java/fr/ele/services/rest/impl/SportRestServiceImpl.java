package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.model.search.SportSearch;
import fr.ele.services.repositories.SportRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.SportRestService;

@Transactional
@Service(SportRestService.SERVER)
public class SportRestServiceImpl extends AbstractRefRestServiceImpl<Sport>
        implements SportRestService {

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
    public Iterable<Sport> findAll() {
        return super.findAll();
    }

    @Override
    public Sport create(Sport model) {
        return super.create(model);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public List<Sport> insertCsv(Attachment file) {
        return insertCsv(file, Sport.class);
    }

    @Override
    public Iterable<Sport> search(SportSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), search);
        return getRepository().findAll(queryBuilder.build());
    }
}
