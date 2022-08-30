package com.bankservices.requests;

public class WallexUserSignupRequest {

	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String language;
	private String countryCode;
	private String accountType;
	private String companyName;

	public WallexUserSignupRequest() {
		super();
	}

	public WallexUserSignupRequest(String email, String firstName, String lastName, String password, String language,
			String countryCode, String accountType, String companyName) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.language = language;
		this.countryCode = countryCode;
		this.accountType = accountType;
		this.companyName = companyName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "WallexUserSignupRequest [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", language=" + language + ", countryCode=" + countryCode
				+ ", accountType=" + accountType + ", companyName=" + companyName + "]";
	}

}
