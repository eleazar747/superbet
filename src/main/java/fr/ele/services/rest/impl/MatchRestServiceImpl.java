package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.Match;
import fr.ele.model.ref.QMatch;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.rest.MatchRestService;

@Transactional
@Service(MatchRestService.SERVER)
public class MatchRestServiceImpl extends AbstractRefRestServiceImpl<Match>
        implements MatchRestService {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    protected MatchRepository getRepository() {
        return matchRepository;
    }

    @Override
    protected QMatch entityPath() {
        return QMatch.match;
    }

}
