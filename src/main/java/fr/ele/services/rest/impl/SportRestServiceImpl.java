package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.model.ref.QSport;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.SportRepository;
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
    public List<Sport> findAll() {
        return super.findAll();
    }

}
