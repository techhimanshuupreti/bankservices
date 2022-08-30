package com.bankservices.constants;

public enum WallexStatusType {
	APPROVED("approved"), 
	REJECTED("rejected"), 
	INACTIVE("inactive"),
	PENDING_PHONE("pending_phone"), 
	PENDING_DOCS("pending_docs"),
	PENDING_VERIFICATION("pending_verification"), 
	PENDING_APPROVAL("pending_approval"),
	DOCUMENT_NEED_RESUBMISSION("documents_need_resubmission"),
	DRAFT("draft"), 
	ACTIVE ("Active")
	;

	private final String value;

	private WallexStatusType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
