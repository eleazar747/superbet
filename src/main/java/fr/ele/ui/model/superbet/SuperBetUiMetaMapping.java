package fr.ele.ui.model.superbet;

import java.util.Arrays;
import java.util.List;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.DataMapping;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;
import fr.ele.ui.model.MetaMapping;
import fr.ele.ui.model.MetaProperty;

public enum SuperBetUiMetaMapping implements MetaMapping {
    BOOKMAKER(BookMakerMapping.values(), BookMaker.class),

    MATCH(MatchMapping.values(), Match.class),

    DATA_MAPPING(DataMappingMapping.values(), DataMapping.class),

    BET_TYPE(BetTypeMapping.values(), BetType.class),

    BOOKMAKER_SYNC(
            BookMakerSynchronizationMapping.values(),
            BookMakerSynchronization.class),

    SPORT(SportMapping.values(), Sport.class);

    private final List<MetaProperty> metaProperties;

    private final Class<?> handledClass;

    private <T extends MetaProperty> SuperBetUiMetaMapping(T[] metaProperties,
            Class<?> handledClass) {
        this.metaProperties = (List<MetaProperty>) Arrays
                .asList(metaProperties);
        this.handledClass = handledClass;
    }

    private SuperBetUiMetaMapping(List<MetaProperty> metaProperties,
            Class<?> handledClass) {
        this.metaProperties = metaProperties;
        this.handledClass = handledClass;
    }

    public List<MetaProperty> getMetaProperties() {
        return metaProperties;
    }

    public String getIdentifier() {
        return name();
    }

    @Override
    public Class<?> handledClass() {
        return handledClass;
    }

}
