package com.bankservices.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseModel {

	@JsonInclude(value=Include.NON_EMPTY)
	private Object result;
	
	@JsonInclude(value=Include.NON_EMPTY)
	private String errorMessage;
	
	@JsonInclude(value=Include.NON_EMPTY)
	private List<String> errors;
	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ResponseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ResponseModel [result=" + result + ", errorMessage=" + errorMessage + ", errors=" + errors + "]";
	}

	public ResponseModel(Object result, String errorMessage, List<String> errors) {
		super();
		this.result = result;
		this.errorMessage = errorMessage;
		this.errors = errors;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
