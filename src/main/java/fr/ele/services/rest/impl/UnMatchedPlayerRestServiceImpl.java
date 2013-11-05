package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.model.QUnMatchedPlayer;
import fr.ele.model.UnMatchedPlayer;
import fr.ele.model.search.UnMatchedPlayerSearch;
import fr.ele.services.repositories.SuperBetRepository;
import fr.ele.services.repositories.UnMatchedPlayerRepository;
import fr.ele.services.repositories.search.SearchMapping;
import fr.ele.services.rest.UnMatchedPlayerRestService;

@Transactional(readOnly = true)
@Service(UnMatchedPlayerRestService.SERVER)
public class UnMatchedPlayerRestServiceImpl extends
        AbstractBaseRestService<UnMatchedPlayer> implements
        UnMatchedPlayerRestService {

    @Autowired
    private UnMatchedPlayerRepository unMatchedPlayerRepository;

    @Override
    protected SuperBetRepository<UnMatchedPlayer> getRepository() {
        return unMatchedPlayerRepository;
    }

    @Override
    public Iterable<UnMatchedPlayer> search(UnMatchedPlayerSearch search) {
        QueryBuilder builder = new QueryBuilder();
        SearchMapping.map(builder, QUnMatchedPlayer.unMatchedPlayer, search);
        return unMatchedPlayerRepository.findAll(builder.build());
    }

    @Override
    @Transactional
    public void delete(long id) {
        super.delete(id);
    }

}
