package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;
import fr.ele.model.search.BetTypeSearch;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.BetTypeRestService;

@Transactional
@Service(BetTypeRestService.SERVER)
public class BetTypeRestServiceImpl extends AbstractRefRestServiceImpl<BetType>
        implements BetTypeRestService {

    @Autowired
    private BetTypeRepository betTypeRepository;

    @Override
    public BetType create(BetType betType) {
        return super.create(betType);
    }

    @Override
    protected QBetType entityPath() {
        return QBetType.betType;
    }

    @Override
    protected SuperBetRepository getRepository() {
        return betTypeRepository;
    }

    @Override
    public Iterable<BetType> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public List<BetType> insertCsv(Attachment file) {
        return insertCsv(file, BetType.class);
    }

    @Override
    public Iterable<BetType> search(BetTypeSearch betTypeSearch) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), betTypeSearch);
        return getRepository().findAll(queryBuilder.build());
    }

}
