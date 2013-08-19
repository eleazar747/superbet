package fr.ele.services.mapping;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.core.TimeTracker;
import fr.ele.model.Bet;
import fr.ele.model.BookMakers;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
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

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private RefKeyRepository refKeyRepository;

    public final long synchronize(T dto) {
        SynchronizerContext context = new SynchronizerContext(getBookMaker()
                .getCode(), dataMappingRepository, sportRepository,
                betTypeRepository, bookMakerRepository, matchRepository,
                refKeyRepository);
        context.setSynchronizationDate(new Date());
        LOGGER.debug("start {} sync at {}", context.getBookMaker().getCode(),
                context.getSynchronizationDate());
        TimeTracker tt = new TimeTracker();
        long nb = convert(context, dto);
        LOGGER.debug("finish {} sync at {} nb {} bets inserted in {}ms",
                context.getBookMaker().getCode(),
                context.getSynchronizationDate(), nb, tt.getDuration());
        return nb;
    }

    protected abstract long convert(SynchronizerContext context, T dto);

    protected abstract BookMakers getBookMaker();

    protected void saveBet(Bet bet) {
        betRepository.save(bet);
    }
}
