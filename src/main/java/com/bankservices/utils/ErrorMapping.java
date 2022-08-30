package com.bankservices.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorMapping {

	private String message;
	private List<String> errors;
	@JsonInclude(value = Include.NON_EMPTY)
	private Map<String, Object> errorsMap;

	public ErrorMapping(String message, List<String> errors, Map<String, Object> errorsMap) {
		super();
		this.message = message;
		this.errors = errors;
		this.errorsMap = errorsMap;
	}

	public Map<String, Object> getErrorsMap() {
		return errorsMap;
	}

	public void setErrorsMap(Map<String, Object> errorsMap) {
		this.errorsMap = errorsMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ErrorMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ErrorMapping [message=" + message + ", errors=" + errors + ", errorsMap=" + errorsMap + "]";
	}

}
