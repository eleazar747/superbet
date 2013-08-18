package fr.ele.services.mapping;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.core.TimeTracker;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.SportRepository;

public abstract class AbstractSynchronizer<T> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataMappingRepository dataMappingRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private BetTypeRepository betTypeRepository;

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Autowired
    private MatchRepository matchRepository;

    public long synchronize(T dto) {
        SynchronizerContext context = new SynchronizerContext("expekt",
                dataMappingRepository, sportRepository, betTypeRepository,
                bookMakerRepository, matchRepository);
        context.setSynchronizationDate(new Date());
        LOGGER.debug("start expekt sync at {}",
                context.getSynchronizationDate());
        TimeTracker tt = new TimeTracker();
        long nb = convert(context, dto);
        LOGGER.debug("finish expekt sync at {} nb {} bets inserted in {}ms",
                context.getSynchronizationDate(), nb, tt.getDuration());
        return nb;
    }

    protected abstract long convert(SynchronizerContext context, T dto);
}
