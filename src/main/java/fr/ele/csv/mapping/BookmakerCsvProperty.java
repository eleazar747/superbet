package fr.ele.csv.mapping;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvProperty;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BookMaker;

public enum BookmakerCsvProperty implements CsvProperty<BookMaker> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<BookMaker> context,
                BookMaker object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue(CsvContext context, BookMaker object, String value) {
            ((SuperBetEntity) object).setId((Long) context.unmarshall(Long.class,
                    value));
        }
    },
    CODE("code") {

        @Override
        public String getStringValue(CsvContext<BookMaker> context,
                BookMaker object) {
            return context.marshall(object.getCode());
        }

        @Override
        public void setValue(CsvContext context, BookMaker object, String value) {
            object.setCode((String) context.unmarshall(String.class, value));
        }
    };

    private final String header;

    private BookmakerCsvProperty(String header) {
        this.header = header;
    }

    @Override
    public String getHeader() {
        return header;
    }

}
