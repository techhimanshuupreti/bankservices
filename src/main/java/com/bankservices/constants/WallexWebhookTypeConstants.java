package com.bankservices.constants;

public enum WallexWebhookTypeConstants {
	USER("user"),
	COLLECTION("collection"), 
	SIMPLE_PAYMENT("simple_payment");

	private final String value;

	private WallexWebhookTypeConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
