package fr.ele.csv;

import fr.ele.model.ref.BookMaker;

public enum CsvRegistry implements CsvBeanProperties {
    BOOKMAKER(BookMaker.class) {

        @Override
        public CsvProperty[] getProperties() {
            return BookmakerCsvProperty.values();
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
