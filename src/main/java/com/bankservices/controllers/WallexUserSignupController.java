package com.bankservices.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankservices.models.ResponseModel;
import com.bankservices.requests.WallexUserSignupRequest;
import com.bankservices.requests.WallexUserBusinessDetailsRequest;
import com.bankservices.requests.WallexUserOnboardingDocumentsRequest;
import com.bankservices.requests.WallexUserOnboardingKycRequest;
import com.bankservices.requests.WallexUserPersonalDetailsRequest;
import com.bankservices.services.WallexDataServices;

@RestController
@RequestMapping("/api/v1/wallex")
public class WallexUserSignupController {
	
	private Logger logger = LoggerFactory.getLogger(WallexUserSignupController.class);

	@Autowired
	private WallexDataServices wallexDataServices;

	@GetMapping("/generate-token")
	public ResponseEntity<ResponseModel> gengenerateWallexApiToken() {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		return wallexDataServices.generateWallexApiToken();
	}
	
	@PostMapping("/user-signup")
	public ResponseEntity<ResponseModel> wallexUserSignup(@Valid @RequestBody WallexUserSignupRequest walleUserSignupRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		return wallexDataServices.signup(walleUserSignupRequest);
	}
	
	@PatchMapping("/personal-details")
	public ResponseEntity<ResponseModel> updatePersonalDetails(@Valid @RequestBody WallexUserPersonalDetailsRequest wallexUserPersonalDetailsRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- " + new Throwable().getStackTrace()[0].getMethodName() + " with request :- " + wallexUserPersonalDetailsRequest);
		return wallexDataServices.updateUserPersonalDetails(wallexUserPersonalDetailsRequest);
	}
	
	@PatchMapping("/business-details")
	public ResponseEntity<ResponseModel> updateBusinessDetails(@Valid @RequestBody WallexUserBusinessDetailsRequest wallexUserBusinessDetailsRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- " + new Throwable().getStackTrace()[0].getMethodName() + " with request :- " + wallexUserBusinessDetailsRequest);
		return wallexDataServices.updateUserBusinessDetails(wallexUserBusinessDetailsRequest);
	}
	
	@PostMapping("/upload-docs")
	public ResponseEntity<ResponseModel> documenUpload(@Valid @RequestBody WallexUserOnboardingDocumentsRequest wallexUserOnboardingDocumentsResuest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- " + new Throwable().getStackTrace()[0].getMethodName() + " with request :- " + wallexUserOnboardingDocumentsResuest);
		return wallexDataServices.uploadMerchantDocuments(wallexUserOnboardingDocumentsResuest);
	}
	
	@PostMapping("/kyc-screen")
	public ResponseEntity<ResponseModel> kycScreen(@Valid @RequestBody WallexUserOnboardingKycRequest wallexUserOnboardingKycRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- " + new Throwable().getStackTrace()[0].getMethodName() + " with request :- " + wallexUserOnboardingKycRequest);
		return wallexDataServices.kycScreen(wallexUserOnboardingKycRequest);
	}

}
