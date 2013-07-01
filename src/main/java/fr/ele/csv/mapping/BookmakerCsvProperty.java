package fr.ele.csv.mapping;

import fr.ele.csv.CsvProperty;
import fr.ele.model.ref.BookMaker;

public enum BookmakerCsvProperty implements CsvProperty<BookMaker> {
    ID("id") {

        @Override
        public String getStringValue(BookMaker object) {
            return String.valueOf(object.getId());
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

        }
    },
    CODE("code") {

        @Override
        public String getStringValue(BookMaker object) {
            return object.getCode();
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

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
