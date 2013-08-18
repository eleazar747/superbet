package fr.ele.services.mapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ele.core.TimeTracker;
import fr.ele.model.DataMapping;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.SportRepository;

public class SynchronizerContext {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(SynchronizerContext.class);

    private final DataMappingRepository dataMappingRepository;

    private final SportRepository sportRepository;

    private final BetTypeRepository betTypeRepository;

    private final BookMakerRepository bookMakerRepository;

    private final MatchRepository matchRepository;

    private Map<String, Sport> sportCache;

    private Map<String, BetType> betTypeCache;

    private BookMaker bookMaker;

    private Date synchronizationDate;

    private final Map<MatchKey, Match> matchCache = new HashMap<MatchKey, Match>();

    private static class MatchKey {
        private final String code, sportCode;

        private final Date date;

        public MatchKey(String code, String sportCode, Date date) {
            this.code = code;
            this.sportCode = sportCode;
            this.date = date;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (code == null ? 0 : code.hashCode());
            result = prime * result + (date == null ? 0 : date.hashCode());
            result = prime * result
                    + (sportCode == null ? 0 : sportCode.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            MatchKey other = (MatchKey) obj;
            if (code == null) {
                if (other.code != null) {
                    return false;
                }
            } else if (!code.equals(other.code)) {
                return false;
            }
            if (date == null) {
                if (other.date != null) {
                    return false;
                }
            } else if (!date.equals(other.date)) {
                return false;
            }
            if (sportCode == null) {
                if (other.sportCode != null) {
                    return false;
                }
            } else if (!sportCode.equals(other.sportCode)) {
                return false;
            }
            return true;
        }

    }

    public SynchronizerContext(String bookmakerCode,
            DataMappingRepository dataMappingRepository,
            SportRepository sportRepository,
            BetTypeRepository betTypeRepository,
            BookMakerRepository bookMakerRepository,
            MatchRepository matchRepository) {
        this.dataMappingRepository = dataMappingRepository;
        this.sportRepository = sportRepository;
        this.betTypeRepository = betTypeRepository;
        this.bookMakerRepository = bookMakerRepository;
        this.matchRepository = matchRepository;
        init(bookmakerCode);
    }

    private void init(String bookmakerCode) {
        LOGGER.debug("start init synchro for {}", bookmakerCode);
        TimeTracker tt = new TimeTracker();
        bookMaker = bookMakerRepository.findByCode(bookmakerCode);
        Iterable<DataMapping> mappings = dataMappingRepository
                .findAll(DataMappingRepository.Queries
                        .findByBookMaker(bookMaker));
        Map<String, String> sportDataMappingCache = new HashMap<String, String>();
        Map<String, String> betTypeDataMappingCache = new HashMap<String, String>();
        for (DataMapping mapping : mappings) {
            switch (mapping.getRefEntityType()) {
            case SPORT:
                sportDataMappingCache.put(mapping.getModelCode(),
                        mapping.getBookMakerCode());
                break;
            case BET_TYPE:
                betTypeDataMappingCache.put(mapping.getModelCode(),
                        mapping.getBookMakerCode());
                break;
            }
        }

        List<Sport> sports = sportRepository.findAll();
        sportCache = new HashMap<String, Sport>(sportDataMappingCache.size());
        for (Sport sport : sports) {
            String code = sportDataMappingCache.get(sport.getCode());
            if (code != null) {
                sportCache.put(code, sport);
            }
        }

        List<BetType> betTypes = betTypeRepository.findAll();
        betTypeCache = new HashMap<String, BetType>(
                betTypeDataMappingCache.size());
        for (BetType betType : betTypes) {
            String code = betTypeDataMappingCache.get(betType.getCode());
            if (code != null) {
                betTypeCache.put(code, betType);
            }
        }
        sportDataMappingCache.clear();
        betTypeDataMappingCache.clear();
        LOGGER.debug("finish init synchro in {}ms ", tt.getDuration());
    }

    public Sport findSport(String bookMakerSportCode) {
        return sportCache.get(bookMakerSportCode);
    }

    public BetType findBetType(String bookMakerBetTypeCode) {
        return betTypeCache.get(bookMakerBetTypeCode);
    }

    public BookMaker getBookMaker() {
        return bookMaker;
    }

    public Date getSynchronizationDate() {
        return synchronizationDate;
    }

    public void setSynchronizationDate(Date synchronizationDate) {
        this.synchronizationDate = synchronizationDate;
    }

    public Match findOrCreateMatch(Sport sport, String matchCode, Date date) {
        MatchKey key = new MatchKey(matchCode, sport.getCode(), date);
        Match match = matchCache.get(key);
        if (match != null) {
            return match;
        }
        match = matchRepository.findByCode(matchCode);
        if (match == null) {
            match = new Match();
            match.setSport(sport);
            match.setDate(date);
            match.setCode(matchCode);
            matchRepository.save(match);
        }
        matchCache.put(createKey(match), match);
        return match;

    }

    private static MatchKey createKey(Match match) {
        return new MatchKey(match.getCode(), match.getSport().getCode(),
                match.getDate());
    }
}
