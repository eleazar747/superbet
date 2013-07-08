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
    @Transactional(readOnly = true)
    public Match findByCode(String code) {
        return matchDao.findByCode(code);
    }

    @Override
    protected GenericDao getDao() {
        return matchDao;
    }

}
