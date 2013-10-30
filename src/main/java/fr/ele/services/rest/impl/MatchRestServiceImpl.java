package fr.ele.services.rest.impl;

import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;
import fr.ele.model.search.MatchSearch;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.MatchRestService;

@Transactional
@Service(MatchRestService.SERVER)
public class MatchRestServiceImpl extends AbstractBaseRestService<Match>
        implements MatchRestService {

    @Override
    @Transactional(readOnly = true)
    public Match findByCode(String code) {
        return matchRepository.findByCode(code);
    }

    @Autowired
    private MatchRepository matchRepository;

    @Override
    protected SuperBetRepository<Match> getRepository() {
        return matchRepository;
    }

    @Override
    public Iterable<Match> findAll() {
        return super.findAll();
    }

    @Override
    public Match create(Match match) {
        return super.create(match);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public List<Match> insertCsv(Attachment file) {
        return insertCsv(file, Match.class);
    }

    @Override
    public Iterable<Match> search(MatchSearch search) {
        QueryBuilder queryBuilder = new QueryBuilder();
        SearchMapping.map(queryBuilder, QMatch.match, search);
        return getRepository().findAll(queryBuilder.build());
    }
}
