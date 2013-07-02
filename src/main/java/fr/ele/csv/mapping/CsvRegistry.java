package fr.ele.csv.mapping;

import fr.ele.csv.CsvBeanProperties;
import fr.ele.csv.CsvProperty;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Sport;
import fr.ele.model.ref.impl.BetTypeImpl;
import fr.ele.model.ref.impl.BookMakerImpl;
import fr.ele.model.ref.impl.SportImpl;

public enum CsvRegistry implements CsvBeanProperties {
    BOOKMAKER(BookMaker.class) {

        @Override
        public CsvProperty[] getProperties() {
            return BookmakerCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new BookMakerImpl();
        }
    },
    SPORT(Sport.class) {

        @Override
        public CsvProperty[] getProperties() {
            return SportCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new SportImpl();
        }
    },
    BET_TYPE(BetType.class) {

        @Override
        public CsvProperty[] getProperties() {
            return BetTypeCsvProperty.values();
        }

        @Override
        public Object getNewInstance() {
            return new BetTypeImpl();
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
