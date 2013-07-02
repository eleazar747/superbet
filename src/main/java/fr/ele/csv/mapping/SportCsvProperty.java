package fr.ele.csv.mapping;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvProperty;
import fr.ele.model.EntityImpl;
import fr.ele.model.ref.Sport;

public enum SportCsvProperty implements CsvProperty<Sport> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<Sport> context, Sport object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue(CsvContext context, Sport object, String value) {
            ((EntityImpl) object).setId((Long) context.unmarshall(Long.class,
                    value));
        }
    },
    CODE("code") {

        @Override
        public String getStringValue(CsvContext<Sport> context, Sport object) {
            return context.marshall(object.getCode());
        }

        @Override
        public void setValue(CsvContext context, Sport object, String value) {
            object.setCode((String) context.unmarshall(String.class, value));
        }
    };
    private final String header;

    private SportCsvProperty(String header) {
        this.header = header;
    }

    @Override
    public String getHeader() {
        return header;
    }
}
