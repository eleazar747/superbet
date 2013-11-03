package fr.ele.csv.mapping;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvProperty;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BetType;

public enum BetTypeCsvProperty implements CsvProperty<BetType> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<BetType> context, BetType object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue(CsvContext context, BetType object, String value) {
            ((SuperBetEntity) object).setId((Long) context.unmarshall(Long.class,
                    value));
        }
    },
    CODE("code") {

        @Override
        public String getStringValue(CsvContext<BetType> context, BetType object) {
            return context.marshall(object.getCode());
        }

        @Override
        public void setValue(CsvContext context, BetType object, String value) {
            object.setCode((String) context.unmarshall(String.class, value));

        }
    };
    private final String header;

    private BetTypeCsvProperty(String header) {
        this.header = header;
    }

    @Override
    public String getHeader() {
        return header;
    }
}
