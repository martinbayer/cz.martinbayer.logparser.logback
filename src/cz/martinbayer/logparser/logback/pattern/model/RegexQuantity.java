package cz.martinbayer.logparser.logback.pattern.model;

public enum RegexQuantity {

	ZERO_TO_MANY("*"), ONE_TO_MANY("+"), ZERO_TO_ONE("?"), DEFAULT("");

	private String quantity;

	RegexQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getQuantity() {
		return this.quantity;
	}
}
