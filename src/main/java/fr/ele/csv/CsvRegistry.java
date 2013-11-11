package fr.ele.csv;

import fr.ele.core.csv.CsvBeanProperties;
import fr.ele.core.csv.CsvProperty;
import fr.ele.csv.mapping.BetTypeCsvProperty;
import fr.ele.csv.mapping.BookmakerCsvProperty;
import fr.ele.csv.mapping.DataMappingCsvProperty;
import fr.ele.csv.mapping.PlayerCsvProperty;
import fr.ele.csv.mapping.SportCsvProperty;
import fr.ele.model.DataMapping;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Player;
import fr.ele.model.ref.Sport;

public enum CsvRegistry implements CsvBeanProperties {
    BOOKMAKER(BookMaker.class) {

        @Override
        public CsvProperty[] getProperties() {
            return BookmakerCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new BookMaker();
        }
    },
    SPORT(Sport.class) {

        @Override
        public CsvProperty[] getProperties() {
            return SportCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new Sport();
        }
    },
    DATA_MAPPING(DataMapping.class) {

        @Override
        public CsvProperty[] getProperties() {
            return DataMappingCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new DataMapping();
        }

    },
    PLAYER(Player.class) {

        @Override
        public CsvProperty[] getProperties() {
            return PlayerCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new Player();
        }

    },
    BET_TYPE(BetType.class) {

        @Override
        public CsvProperty[] getProperties() {
            return BetTypeCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new BetType();
        }
    };

    private final Class<?> handledClass;

    private CsvRegistry(Class<?> handledClass) {
        this.handledClass = handledClass;
    }

    @Override
    public Class getHandledClass() {
        return handledClass;
    }

    public static CsvBeanProperties findCsvDefinition(Class<?> clazz) {
        for (CsvRegistry registry : CsvRegistry.values()) {
            if (registry.getHandledClass().isAssignableFrom(clazz)) {
                return registry;
            }
        }
        throw new RuntimeException(clazz.getName() + "not handled");
    }
}
