package fr.ele.services.mapping;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import fr.ele.core.TimeTracker;
import fr.ele.model.DataMapping;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;

public class SynchronizerContext {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(SynchronizerContext.class);

    private final DataMappingRepository dataMappingRepository;

    private final SportRepository sportRepository;

    private final BetTypeRepository betTypeRepository;

    private final BookMakerRepository bookMakerRepository;

    private final MatchRepository matchRepository;

    private final RefKeyRepository refKeyRepository;

    private Map<String, Sport> sportCache;

    private Map<String, BetType> betTypeCache;

    private Map<String, String> teamCache;

    private BookMaker bookMaker;

    private Date synchronizationDate;

    private RefKey refKeyCache;

    private final Map<MatchKey, Match> matchCache = new HashMap<>();

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
            MatchRepository matchRepository, RefKeyRepository refKeyRepository) {
        this.dataMappingRepository = dataMappingRepository;
        this.sportRepository = sportRepository;
        this.betTypeRepository = betTypeRepository;
        this.bookMakerRepository = bookMakerRepository;
        this.matchRepository = matchRepository;
        this.refKeyRepository = refKeyRepository;
        init(bookmakerCode);
    }

    private void init(String bookmakerCode) {
        LOGGER.debug("start init synchro for {}", bookmakerCode);
        TimeTracker tt = new TimeTracker();
        bookMaker = bookMakerRepository.findByCode(bookmakerCode);
        Iterable<DataMapping> mappings = dataMappingRepository
                .findAll(DataMappingRepository.Queries
                        .findByBookMaker(bookMaker));
        Multimap<String, String> sportDataMappingCache = HashMultimap.create();
        Multimap<String, String> betTypeDataMappingCache = HashMultimap
                .create();
        Multimap<String, String> teamDataMappingCache = HashMultimap.create();
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

            case TEAM:
                teamDataMappingCache.put(mapping.getModelCode(),
                        mapping.getBookMakerCode());
                break;
            }
        }

        List<Sport> sports = sportRepository.findAll();
        sportCache = new HashMap<>(sportDataMappingCache.size());
        for (Sport sport : sports) {
            Collection<String> codes = sportDataMappingCache.get(sport
                    .getCode());
            if (codes != null && !codes.isEmpty()) {
                for (String code : codes) {
                    sportCache.put(code, sport);
                }
            }
        }

        List<BetType> betTypes = betTypeRepository.findAll();
        betTypeCache = new HashMap<>(betTypeDataMappingCache.size());
        for (BetType betType : betTypes) {
            Collection<String> codes = betTypeDataMappingCache.get(betType
                    .getCode());
            if (codes != null && !codes.isEmpty()) {
                for (String code : codes) {
                    betTypeCache.put(code, betType);
                }
            }
        }

        teamCache = new HashMap<>(teamDataMappingCache.size());

        for (String key : teamDataMappingCache.keys()) {
            Collection<String> codes = teamDataMappingCache.get(key);
            if (codes != null && !codes.isEmpty()) {
                for (String code : codes) {
                    teamCache.put(code, key);
                }
            }
        }

        sportDataMappingCache.clear();
        betTypeDataMappingCache.clear();
        LOGGER.debug("finish init synchro in {}ms ", tt.getDuration());
    }

    public Sport findSport(String bookMakerSportCode) {
        return sportCache.get(bookMakerSportCode);
    }

    public String findTeam(Sport sport, String bookMakerSportCode) {
        return teamCache.get(bookMakerSportCode);
    }

    public BetType findBetType(String bookMakerBetTypeCode) {
        return betTypeCache.get(bookMakerBetTypeCode);
    }

    public BookMaker getBookMaker() {
        return bookMaker;
    }

    public void setBookmaker(String bookmakerCode) {
        bookMaker = bookMakerRepository.findByCode(bookmakerCode);

    }

    public void setBookmaker(BookMaker bookmakerCode) {
        bookMaker = bookmakerCode;

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

    public Match findOrCreateMatch(Sport sport, Date date, String... players) {
        String matchCode = StringUtils.join(players, "**");
        return findOrCreateMatch(sport, matchCode, date);
    }

    private static MatchKey createKey(Match match) {
        return new MatchKey(match.getCode(), match.getSport().getCode(),
                match.getDate());
    }

    public RefKey findOrCreateRefKey(Match match, BetType betType) {
        if (refKeyCache != null
                && refKeyCache.getBetType().getId() == betType.getId()
                && match.getId() == refKeyCache.getMatch().getId()) {
            return refKeyCache;
        }
        RefKey refKey = refKeyRepository.findOne(RefKeyRepository.Queries
                .findRefKey(betType, match));
        if (refKey == null) {
            refKey = new RefKey();
            refKey.setBetType(betType);
            refKey.setMatch(match);
            refKeyRepository.save(refKey);
        }
        refKeyCache = refKey;
        return refKey;
    }
}
