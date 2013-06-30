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
		public static final String MATCH_COLUMN = "MATCH";

	}
	public static final class SportTable {
		public static final String TABLE = "SPORT";
		public static final String CODE_COLUMN = "ID";
		public static final String SPORT_COLUMN = "SPORT";

	}
	public static final class BetTypeTable {
		public static final String TABLE = "BET_TYPE";
		public static final String CODE_COLUMN = "CODE";
		

	}
	public static final class RefKeyTable {
		public static final String TABLE = "REF_KEY";
		public static final String MATCHID_COLUMN = "MATCH_ID";
		public static final String BETID_COLUMN = "BET_ID";
		

	}
}
