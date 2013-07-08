package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.Match;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.dao.MatchDao;
import fr.ele.services.rest.MatchRestService;

@Transactional
@Service(MatchRestService.SERVER)
public class MatchRestServiceImpl extends AbstractRefRestServiceImpl<Match>
        implements MatchRestService {

    @Autowired
    private MatchDao matchDao;

    @Override
    public Match findByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected GenericDao getDao() {
        return matchDao;
    }

}
