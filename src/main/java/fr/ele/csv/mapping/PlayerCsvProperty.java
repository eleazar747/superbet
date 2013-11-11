package fr.ele.csv.mapping;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvProperty;
import fr.ele.model.SuperBetEntity;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Player;
import fr.ele.model.ref.Sport;

public enum PlayerCsvProperty implements CsvProperty<Player> {
    ID("id") {

        @Override
        public String getStringValue(CsvContext<Player> context, Player object) {
            return context.marshall(object.getId());
        }

        @Override
        public void setValue(CsvContext context, Player object, String value) {
            ((SuperBetEntity) object).setId((Long) context.unmarshall(
                    Long.class, value));
        }
    },
    DATA_PROVIDER("dataprovider") {

        @Override
        public String getStringValue(CsvContext<Player> context, Player object) {
            return context.marshall(object.getDataProvider().getCode());
        }

        @Override
        public void setValue(CsvContext context, Player object, String value) {
            BookMaker bookMaker = context.getGraphResolver().findByCode(
                    BookMaker.class,
                    (String) context.unmarshall(String.class, value));
            object.setDataProvider(bookMaker);
        }
    },
    SPORT("sport") {

        @Override
        public String getStringValue(CsvContext<Player> context, Player object) {
            return context.marshall(object.getSport().getCode());
        }

        @Override
        public void setValue(CsvContext context, Player object, String value) {
            Sport sport = context.getGraphResolver().findByCode(Sport.class,
                    (String) context.unmarshall(String.class, value));
            object.setSport(sport);
        }
    },
    CODE("code") {

        @Override
        public String getStringValue(CsvContext<Player> context, Player object) {
            return context.marshall(object.getCode());
        }

        @Override
        public void setValue(CsvContext context, Player object, String value) {
            object.setCode((String) context.unmarshall(String.class, value));
        }
    };

    private final String header;

    private PlayerCsvProperty(String header) {
        this.header = header;
    }

    @Override
    public String getHeader() {
        return header;
    }
}
