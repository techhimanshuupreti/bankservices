package com.bankservices.requests;

public class WallexUserBusinessDetailsRequest {

	private String countryOfIncorporation;
	private String countryOfOperations;
	private String businessType;
	private String businessAddress;
	private String businessPinCode;
	private String businessState;
	private String businessCity;
	private String registrationNumber;
	private String incorporationDate;
	private String wallexUserId;

	public WallexUserBusinessDetailsRequest() {
		super();
	}

	public WallexUserBusinessDetailsRequest(String countryOfIncorporation, String countryOfOperations,
			String businessType, String businessAddress, String businessPinCode, String businessState,
			String businessCity, String registrationNumber, String incorporationDate, String wallexUserId) {
		super();
		this.countryOfIncorporation = countryOfIncorporation;
		this.countryOfOperations = countryOfOperations;
		this.businessType = businessType;
		this.businessAddress = businessAddress;
		this.businessPinCode = businessPinCode;
		this.businessState = businessState;
		this.businessCity = businessCity;
		this.registrationNumber = registrationNumber;
		this.incorporationDate = incorporationDate;
		this.wallexUserId = wallexUserId;
	}

	public String getWallexUserId() {
		return wallexUserId;
	}

	public void setWallexUserId(String wallexUserId) {
		this.wallexUserId = wallexUserId;
	}

	public String getCountryOfIncorporation() {
		return countryOfIncorporation;
	}

	public void setCountryOfIncorporation(String countryOfIncorporation) {
		this.countryOfIncorporation = countryOfIncorporation;
	}

	public String getCountryOfOperations() {
		return countryOfOperations;
	}

	public void setCountryOfOperations(String countryOfOperations) {
		this.countryOfOperations = countryOfOperations;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(String incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessPinCode() {
		return businessPinCode;
	}

	public void setBusinessPinCode(String businessPinCode) {
		this.businessPinCode = businessPinCode;
	}

	public String getBusinessState() {
		return businessState;
	}

	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}

	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}

	@Override
	public String toString() {
		return "WallexUserBusinessDetailsRequest [countryOfIncorporation=" + countryOfIncorporation
				+ ", countryOfOperations=" + countryOfOperations + ", businessType=" + businessType
				+ ", businessAddress=" + businessAddress + ", businessPinCode=" + businessPinCode + ", businessState="
				+ businessState + ", businessCity=" + businessCity + ", registrationNumber=" + registrationNumber
				+ ", incorporationDate=" + incorporationDate + ", wallexUserId=" + wallexUserId + "]";
	}

}
