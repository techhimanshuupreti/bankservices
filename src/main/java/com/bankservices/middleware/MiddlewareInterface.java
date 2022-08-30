package com.bankservices.middleware;

import org.springframework.http.ResponseEntity;

import com.bankservices.models.ResponseModel;

public interface MiddlewareInterface {

	public ResponseEntity<ResponseModel> isValidRequest(Object validateRequestObj);
	public ResponseEntity<ResponseModel> isWebhookValidRequest(Object validateRequestObj);
	
	public ResponseEntity<ResponseModel> isAuthenticateRequest(Object authenticateRequestObj);
	public ResponseEntity<ResponseModel> isWebhookAuthenticateRequest(Object authenticateRequestObj);
}
