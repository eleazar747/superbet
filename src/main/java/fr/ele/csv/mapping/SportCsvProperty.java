package fr.ele.csv.mapping;

import fr.ele.csv.CsvContext;
import fr.ele.csv.CsvProperty;
import fr.ele.model.ref.Sport;

public enum SportCsvProperty implements CsvProperty<Sport> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<Sport> context, Sport object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

        }
    },
    CODE("code") {

        @Override
        public String getStringValue(CsvContext<Sport> context, Sport object) {
            return context.marshall(object.getCode());
        }

        @Override
        public void setValue() {
            // TODO Auto-generated method stub

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
