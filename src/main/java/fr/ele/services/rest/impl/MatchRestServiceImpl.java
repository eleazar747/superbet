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
import fr.ele.dto.MatchDto;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;
import fr.ele.model.search.MatchSearch;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.MatchRestService;

@Transactional(readOnly = true)
@Service(MatchRestService.SERVER)
public class MatchRestServiceImpl extends
        AbstractRefRestServiceImpl<MatchDto, Match> implements MatchRestService {

    @Override
    @Timed
    public MatchDto findByCode(String code) {
        return super.findByCode(code);
    }

    @Autowired
    private MatchRepository matchRepository;

    @Override
    protected SuperBetRepository<Match> getRepository() {
        return matchRepository;
    }

    @Override
    @Timed
    public Iterable<MatchDto> findAll() {
        return super.findAll();
    }

    @Override
    @Timed
    @Transactional
    public MatchDto create(MatchDto match) {
        return super.create(match);
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
    public List<MatchDto> insertCsv(Attachment file) {
        return insertCsv(file, Match.class);
    }

    @Override
    @Timed
    public Iterable<MatchDto> search(MatchSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, QMatch.match, search);
        Iterable<Match> models = getRepository().findAll(queryBuilder.build());
        return Translate.to(dtoClass()).fromEach(Lists.newArrayList(models));
    }

    @Override
    protected Class<Match> modelClass() {
        return Match.class;
    }

    @Override
    protected Class<MatchDto> dtoClass() {
        return MatchDto.class;
    }

    @Override
    @Timed
    public MatchDto get(long id) {
        return super.get(id);
    }

    @Override
    protected QMatch entityPath() {
        return QMatch.match;
    }

}
