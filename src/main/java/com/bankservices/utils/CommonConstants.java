package com.bankservices.utils;

public enum CommonConstants {

	SUCCESS("SUCCESS"), 
	ERROR("ERROR"),;

	private final String value;

	private CommonConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
