package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.Match;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.SuperBetRepository;
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
    public List<Match> findAll() {
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

}
