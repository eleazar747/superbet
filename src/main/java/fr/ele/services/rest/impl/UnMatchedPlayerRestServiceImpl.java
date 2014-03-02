package fr.ele.services.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codahale.metrics.annotation.Timed;
import com.codiform.moo.curry.Translate;
import com.google.common.collect.Lists;

import fr.ele.core.search.querydsl.QueryBuilder;
import fr.ele.dto.UnMatchedPalyerDto;
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
        AbstractBaseRestService<UnMatchedPalyerDto, UnMatchedPlayer> implements
        UnMatchedPlayerRestService {

    @Autowired
    private UnMatchedPlayerRepository unMatchedPlayerRepository;

    @Override
    protected SuperBetRepository<UnMatchedPlayer> getRepository() {
        return unMatchedPlayerRepository;
    }

    @Override
    @Timed
    public Iterable<UnMatchedPalyerDto> search(UnMatchedPlayerSearch search) {
        QueryBuilder builder = new QueryBuilder();
        SearchMapping.map(builder, QUnMatchedPlayer.unMatchedPlayer, search);
        Iterable<UnMatchedPlayer> models = getRepository().findAll(
                builder.build());
        return Translate.to(dtoClass()).fromEach(Lists.newArrayList(models));
    }

    @Override
    @Transactional
    @Timed
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    protected Class<UnMatchedPlayer> modelClass() {
        return UnMatchedPlayer.class;
    }

    @Override
    protected Class<UnMatchedPalyerDto> dtoClass() {
        return UnMatchedPalyerDto.class;
    }

    @Override
    @Timed
    public Iterable<UnMatchedPalyerDto> findAll() {
        return super.findAll();
    }

    @Override
    @Timed
    public UnMatchedPalyerDto get(long id) {
        return super.get(id);
    }

}
