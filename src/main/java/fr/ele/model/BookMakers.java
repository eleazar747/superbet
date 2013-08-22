package fr.ele.model;

public enum BookMakers {

	BETCLICK("betclick"), EXPEKT("expekt"), NORDICBET("nordicbet"), INTERTOPS(
			"intertops");

	private final String code;

	private BookMakers(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
