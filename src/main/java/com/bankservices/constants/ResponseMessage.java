package com.bankservices.constants;

public enum ResponseMessage {
	
	//Common Message
	API_TOKEN_NOT_GENERATE ("Api token is not generated."),
	API_REQUEST_WRONG ("You may have missed or invalid out the X-Api-Key in your request header."),
	INVALID_REQUEST ("Invalid request of "),
	
	WALLEX_ALL_REQUEST_FIELDS_EMPTY ("All parameters are an empty."),
	WALLEX_ALL_REQUEST_FIELDS_VALID ("All parameters are an valid."),
	WALLEX_ALL_REQUEST_FIELDS_AUTH ("All parameters are an authenticate."),
	WALLEX_FEW_REQUEST_FIELDS_INVALID ("Parameters are an empty or invalid."),
	WALLEX_REQUEST_FIELDS_INVALID (" is missing or invalid."),
	
	//Wallex Signup User
	WALLEX_USER_NOT_REGISTERED_IN_WALLEX ("User is not registered in wallex"),
	
	//Wallex Onboarding User docs
	WALLEX_DOC_NOT_UPLOAD ("Onboarding documents are not uploaded."),
	WALLEX_DOC_UPLOAD ("Onboarding documents are uploaded."),
	
	//Wallex Onboarding User kyc screen
	WALLEX_KYC_DONE ("Onboarding kyc are successfully."),
	WALLEX_KYC_NOT_DONE ("Onboarding kyc are unsuccessfully."),
	
	//Webhook
	WALLEX_WEBHOOK_RESOURCE_TYPE ("Invalid resource type"),
	
	// missing or invalid fields
	FIELDS_INVALID_MISSING ("Fields are missing or invalid."),
	;
	
	private final String value;

	private ResponseMessage(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
