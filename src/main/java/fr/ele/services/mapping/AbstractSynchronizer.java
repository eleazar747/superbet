package fr.ele.services.mapping;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.core.TimeTracker;
import fr.ele.model.Bet;
import fr.ele.model.BookMakerSynchronization;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.BookMakerSynchronizationRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;
import fr.ele.services.repositories.UnMatchedPlayerRepository;

public abstract class AbstractSynchronizer<T> implements SynchronizerService<T> {
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

    @Autowired
    private BookMakerSynchronizationRepository bookMakerSynchronizationRepository;

    @Autowired
    private UnMatchedPlayerRepository unMatchedPlayerRepository;

    @Autowired
    private Proxy proxy;

    @Override
    public final BookMakerSynchronization synchronize(String bookmakerCode,
            T dto) {
        SynchronizerContext context = new MappingControlContext(bookmakerCode,
                dataMappingRepository, sportRepository, betTypeRepository,
                bookMakerRepository, matchRepository, refKeyRepository,
                unMatchedPlayerRepository);
        context.setSynchronizationDate(new Date());
        LOGGER.debug("start {} sync at {}", context.getBookMaker().getCode(),
                context.getSynchronizationDate());
        TimeTracker tt = new TimeTracker();
        long nb = convert(context, dto);
        LOGGER.debug("finish {} sync at {} nb {} bets inserted in {}ms",
                context.getBookMaker().getCode(),
                context.getSynchronizationDate(), nb, tt.getDuration());
        BookMakerSynchronization sync = new BookMakerSynchronization();
        sync.setBookMaker(context.getBookMaker());
        sync.setDuration(tt.getDuration());
        sync.setNbBets(nb);
        sync.setSynchronizationDate(context.getSynchronizationDate());
        bookMakerSynchronizationRepository.save(sync);
        return sync;
    }

    protected abstract long convert(SynchronizerContext context, T dto);

    protected abstract Class<T> getDtoClass();

    protected void saveBet(Bet bet) {
        betRepository.save(bet);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T unmarshall(InputStream inputStream, String charset) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(getDtoClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Reader reader;
            if (charset != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream,
                        charset));
                return (T) unmarshaller.unmarshal(reader);
            }
            return (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T unmarshall(String urlBase, String encoding) throws Exception {
        URL url = new URL(urlBase);
        URLConnection connection = url.openConnection(proxy);
        InputStream inputStream = connection.getInputStream();
        return unmarshall(new BufferedInputStream(inputStream, 5000), encoding);
    }

}
