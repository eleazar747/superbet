package fr.ele.services.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ele.model.DataMapping;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.SportRepository;

public class SynchronizerContext {
    private final DataMappingRepository dataMappingRepository;

    private final SportRepository sportRepository;

    private final BetTypeRepository betTypeRepository;

    private final BookMakerRepository bookMakerRepository;

    private Map<String, Sport> sportCache;

    private Map<String, BetType> betTypeCache;

    private BookMaker bookMaker;

    public SynchronizerContext(String bookmakerCode,
            DataMappingRepository dataMappingRepository,
            SportRepository sportRepository,
            BetTypeRepository betTypeRepository,
            BookMakerRepository bookMakerRepository) {
        this.dataMappingRepository = dataMappingRepository;
        this.sportRepository = sportRepository;
        this.betTypeRepository = betTypeRepository;
        this.bookMakerRepository = bookMakerRepository;
        init(bookmakerCode);
    }

    private void init(String bookmakerCode) {
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
}
