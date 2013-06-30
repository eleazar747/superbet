package fr.ele.model;

public class SuperBetTables {
    public static final String ID_COLUMN = "ID";

    public static final class BookMakerTable {
        public static final String TABLE = "BOOKMAKER";

        public static final String CODE_COLUMN = "CODE";
    }

    public static final class MatchTable {
        public static final String TABLE = "MATCH";

        public static final String CODE_COLUMN = "CODE";

        public static final String DATE_COLUMN = "DATE";

        public static final String SPORT_COLUMN = "SPORT_ID";

    }

    public static final class SportTable {
        public static final String TABLE = "SPORT";

        public static final String CODE_COLUMN = "CODE";

    }

    public static final class BetTypeTable {
        public static final String TABLE = "BET_TYPE";

        public static final String CODE_COLUMN = "CODE";

    }

    public static final class RefKeyTable {
        public static final String TABLE = "REF_KEY";

        public static final String MATCH_ID_COLUMN = "MATCH_ID";

        public static final String BETTYPE_ID_COLUMN = "BET_ID";

    }

    public static final class BetTable {
        public static final String TABLE = "BET";

        public static final String ODD_COLUMN = "ODD";

        public static final String DATE = "DATE";

        public static final String REFKEY_ID_COLUMN = "REFKEY_ID";

        public static final String BOOKMAKER_ID_COLUMN = "BOOKMAKER_ID";
    }
}
