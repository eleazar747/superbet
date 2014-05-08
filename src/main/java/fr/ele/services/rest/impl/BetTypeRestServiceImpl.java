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
import fr.ele.dto.BetTypeDto;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;
import fr.ele.model.search.BetTypeSearch;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.BetTypeRestService;

@Transactional(readOnly = true)
@Service(BetTypeRestService.SERVER)
public class BetTypeRestServiceImpl extends
AbstractRefRestServiceImpl<BetTypeDto, BetType> implements
BetTypeRestService {

    @Autowired
    private BetTypeRepository betTypeRepository;

    @Override
    @Transactional
    @Timed
    public BetTypeDto create(BetTypeDto betType) {
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
    @Timed
    public Iterable<BetTypeDto> findAll() {
        return super.findAll();
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
    public List<BetTypeDto> insertCsv(Attachment file) {
        return insertCsv(file, BetType.class);
    }

    @Override
    @Timed
    public Iterable<BetTypeDto> search(BetTypeSearch betTypeSearch) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, entityPath(), betTypeSearch);
        Iterable models = getRepository().findAll(queryBuilder.build());
        return Translate.to(dtoClass()).fromEach(Lists.newArrayList(models));
    }

    @Override
    protected Class<BetType> modelClass() {
        return BetType.class;
    }

    @Override
    protected Class<BetTypeDto> dtoClass() {
        return BetTypeDto.class;
    }

    @Override
    @Timed
    public BetTypeDto findByCode(String code) {
        return super.findByCode(code);
    }

    @Override
    @Timed
    public BetTypeDto get(long id) {
        return super.get(id);
    }

}
