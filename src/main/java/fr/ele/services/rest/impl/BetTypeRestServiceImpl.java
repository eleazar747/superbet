package fr.ele.services.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.path.EntityPathBase;

import fr.ele.model.ref.BetType;
import fr.ele.model.ref.QBetType;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.SuperBetRepository;
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
    protected EntityPathBase entityPath() {
        return QBetType.betType;
    }

    @Override
    protected SuperBetRepository getRepository() {
        return betTypeRepository;
    }

    @Override
    public List<BetType> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

}
