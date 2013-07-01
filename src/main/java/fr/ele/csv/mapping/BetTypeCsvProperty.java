package fr.ele.csv.mapping;

import fr.ele.csv.CsvProperty;
import fr.ele.model.ref.BetType;

public enum BetTypeCsvProperty implements CsvProperty<BetType> {
    ID("id") {

        @Override
        public String getStringValue(BetType object) {
            return String.valueOf(object.getId());
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

        }
    },
    CODE("code") {

        @Override
        public String getStringValue(BetType object) {
            return object.getCode();
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

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
