package com.bankservices.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class WalleUserSignupResponse {

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String id;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String email;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String firstName;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String lastName;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String language;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String countryCode;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String accountType;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String companyName;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String registrationDate;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String username;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String userId;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String accountId;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String employmentPosition;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String mobileCountryCode;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String gender;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String city;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String mobileNumber;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String postalCode;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String dateOfBirth;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String identificationType;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String countryOfResidence;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String employmentStatus;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String expiryDate;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String employmentIndustry;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String nationality;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String countryOfBirth;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String residentialAddress;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String identificationNumber;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String issueDate;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String status;

	public WalleUserSignupResponse() {
		super();
	}

	public WalleUserSignupResponse(String id, String email, String firstName, String lastName, String language,
			String countryCode, String accountType, String companyName, String registrationDate, String username,
			String userId, String accountId, String employmentPosition, String mobileCountryCode, String gender,
			String city, String mobileNumber, String postalCode, String dateOfBirth, String identificationType,
			String countryOfResidence, String employmentStatus, String expiryDate, String employmentIndustry,
			String nationality, String countryOfBirth, String residentialAddress, String identificationNumber,
			String issueDate, String status) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.language = language;
		this.countryCode = countryCode;
		this.accountType = accountType;
		this.companyName = companyName;
		this.registrationDate = registrationDate;
		this.username = username;
		this.userId = userId;
		this.accountId = accountId;
		this.employmentPosition = employmentPosition;
		this.mobileCountryCode = mobileCountryCode;
		this.gender = gender;
		this.city = city;
		this.mobileNumber = mobileNumber;
		this.postalCode = postalCode;
		this.dateOfBirth = dateOfBirth;
		this.identificationType = identificationType;
		this.countryOfResidence = countryOfResidence;
		this.employmentStatus = employmentStatus;
		this.expiryDate = expiryDate;
		this.employmentIndustry = employmentIndustry;
		this.nationality = nationality;
		this.countryOfBirth = countryOfBirth;
		this.residentialAddress = residentialAddress;
		this.identificationNumber = identificationNumber;
		this.issueDate = issueDate;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getEmploymentPosition() {
		return employmentPosition;
	}

	public void setEmploymentPosition(String employmentPosition) {
		this.employmentPosition = employmentPosition;
	}

	public String getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WalleUserSignupResponse [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", language=" + language + ", countryCode=" + countryCode + ", accountType=" + accountType
				+ ", companyName=" + companyName + ", registrationDate=" + registrationDate + ", username=" + username
				+ ", userId=" + userId + ", accountId=" + accountId + ", employmentPosition=" + employmentPosition
				+ ", mobileCountryCode=" + mobileCountryCode + ", gender=" + gender + ", city=" + city
				+ ", mobileNumber=" + mobileNumber + ", postalCode=" + postalCode + ", dateOfBirth=" + dateOfBirth
				+ ", identificationType=" + identificationType + ", countryOfResidence=" + countryOfResidence
				+ ", employmentStatus=" + employmentStatus + ", expiryDate=" + expiryDate + ", employmentIndustry="
				+ employmentIndustry + ", nationality=" + nationality + ", countryOfBirth=" + countryOfBirth
				+ ", residentialAddress=" + residentialAddress + ", identificationNumber=" + identificationNumber
				+ ", issueDate=" + issueDate + ", status=" + status + "]";
	}

}
