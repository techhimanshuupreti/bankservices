package com.bankservices.constants;

public enum CommonConstant {
	// Date format
	INPUT_DATE_FORMAT("dd-MM-yyyy"), 
	OUTPUT_DATE_FORMAT("yyyy-MM-dd"), 
	OUTPUT_DATE_FORMAT_DB("yyyy-MM-dd HH:mm:ss"),
	DATE_TIME_FORMAT("dd-MM-yyyy 'T' HH:mm:ss"), 
	INPUT_DATE_FOR_MIS_REPORT("dd/MM/yyyy"), 
	COMMA(","),
	TO_TIME_FORMAT(" 00:00:00"), 
	FROM_TIME_FORMAT(" 23:59:59"),;

	private final String value;

	private CommonConstant(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
