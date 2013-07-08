package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.Sport;
import fr.ele.services.dao.GenericDao;
import fr.ele.services.dao.SportDao;
import fr.ele.services.rest.SportRestService;

@Transactional
@Service(SportRestService.SERVER)
public class SportRestServiceImpl extends AbstractRefRestServiceImpl<Sport>
        implements SportRestService {

    @Autowired
    private SportDao sportDao;

    @Override
    public Sport findByCode(String code) {
        return null;
    }

    @Override
    protected GenericDao getDao() {
        return sportDao;
    }

}
