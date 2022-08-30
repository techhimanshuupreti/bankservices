package com.bankservices.requests;

public class WallexUserPersonalDetailsRequest {

	private String mobileCountryCode;
	private String mobileNumber;
	private String gender;
	private String countryOfBirth;
	private String nationality;
	private String residentialAddress;
	private String city;
	private String postalCode;
	private String dateOfBirth;
	private String identificationType;
	private String identificationNumber;
	private String issueDate;
	private String expiryDate;
	private String employmentIndustry;
	private String employmentStatus;
	private String employmentPosition;
	private String wallexUserId;

	public WallexUserPersonalDetailsRequest() {
		super();
	}
	
	public WallexUserPersonalDetailsRequest(String mobileCountryCode, String mobileNumber, String gender,
			String countryOfBirth, String nationality, String residentialAddress, String city, String postalCode,
			String dateOfBirth, String identificationType, String identificationNumber, String issueDate,
			String expiryDate, String employmentIndustry, String employmentStatus, String employmentPosition,
			String wallexUserId) {
		super();
		this.mobileCountryCode = mobileCountryCode;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.countryOfBirth = countryOfBirth;
		this.nationality = nationality;
		this.residentialAddress = residentialAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.dateOfBirth = dateOfBirth;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		this.employmentIndustry = employmentIndustry;
		this.employmentStatus = employmentStatus;
		this.employmentPosition = employmentPosition;
		this.wallexUserId = wallexUserId;
	}

	public String getWallexUserId() {
		return wallexUserId;
	}

	public void setWallexUserId(String wallexUserId) {
		this.wallexUserId = wallexUserId;
	}

	public String getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getEmploymentIndustry() {
		return employmentIndustry;
	}

	public void setEmploymentIndustry(String employmentIndustry) {
		this.employmentIndustry = employmentIndustry;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getEmploymentPosition() {
		return employmentPosition;
	}

	public void setEmploymentPosition(String employmentPosition) {
		this.employmentPosition = employmentPosition;
	}

	@Override
	public String toString() {
		return "WallexUserPersonalDetailsRequest [mobileCountryCode=" + mobileCountryCode + ", mobileNumber="
				+ mobileNumber + ", gender=" + gender + ", countryOfBirth=" + countryOfBirth + ", nationality="
				+ nationality + ", residentialAddress=" + residentialAddress + ", city=" + city + ", postalCode="
				+ postalCode + ", dateOfBirth=" + dateOfBirth + ", identificationType=" + identificationType
				+ ", identificationNumber=" + identificationNumber + ", issueDate=" + issueDate + ", expiryDate="
				+ expiryDate + ", employmentIndustry=" + employmentIndustry + ", employmentStatus=" + employmentStatus
				+ ", employmentPosition=" + employmentPosition + "]";
	}

}
