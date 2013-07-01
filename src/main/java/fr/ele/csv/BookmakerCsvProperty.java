package fr.ele.csv;

import fr.ele.model.ref.BookMaker;

public enum BookmakerCsvProperty implements CsvProperty<BookMaker> {
    ID("id", "id") {

        @Override
        public String getStringValue(BookMaker object) {
            return String.valueOf(object.getId());
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

        }
    },
    CODE("code", "code") {

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

    private final String propertyBean;

    private BookmakerCsvProperty(String header, String propertyBean) {
        this.header = header;
        this.propertyBean = propertyBean;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getPropertyBean() {
        return propertyBean;
    }
}
