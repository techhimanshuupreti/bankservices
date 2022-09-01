package com.bankservices.controllers;

import java.util.Map;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankservices.dto.WallexWebhookDTO;
import com.bankservices.middleware.WallexMiddleware;
import com.bankservices.models.ResponseModel;
import com.bankservices.response.WallexDepositResponse;
import com.bankservices.services.WallexDataServices;
import com.bankservices.utils.PropertiesManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/wallex/webhook")
public class WallexWebhookController {
	private Logger logger = LoggerFactory.getLogger(WallexWebhookController.class.getName());

	@Autowired
	private WallexDataServices wallexDataService;

	@Autowired
	private WallexMiddleware wallexMiddleware;

//	@PostMapping("/push")
//	public ResponseEntity<ResponseModel> vaPush(@RequestHeader Map<String, String> allHeaders, @RequestBody WallexWebhookDTO wallexWebhookDTO) {

//		logger.info("InSide Class Name :- " + getClass() + ", Method :- " + new Throwable().getStackTrace()[0].getMethodName() + " with Resource id :- " + wallexWebhookDTO.getResourceId());
//		logger.info("Response :- " + wallexWebhookDTO.toString());
//		logger.info("All Headers " + allHeaders);
//		String x_api_key = propertiesManager.getSystemProperty("WALLEX_X_API_KEY_VA_PUSH");
//
//		String header_x_ap_key = allHeaders.get("x-api-key");
//		if(header_x_ap_key.compareTo(x_api_key) != 0) {
//			ResponseModel responseModel = new ResponseModel();
//			responseModel.setResult(null);
//			responseModel.setErrorMessage(HttpStatus.FORBIDDEN.name());
//			return new ResponseEntity<>(responseModel, HttpStatus.FORBIDDEN);
//		}
//		ResponseEntity<ResponseModel> res = null;
//		if(wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.USER.getValue()))
//			res = wallexDataService.getUserByResourceId(wallexWebhookDTO);
//		else if(wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.COLLECTION.getValue()))
//			res = wallexDataService.getCollectionByResourceId(wallexWebhookDTO);
//		else if(wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.SIMPLE_PAYMENT.getValue()))
//			res = wallexDataService.getTransferByResourceId(wallexWebhookDTO);
//		//ResponseEntity<ResponseModel> res = wallexDataService.ApiCall(wallexCollectionWebhookDTO);
//		return res;
//	}

	@PostMapping("/pushs")
	public ResponseEntity<ResponseModel> vaPushs(@RequestHeader Map<String, String> allHeaders,
			@RequestBody WallexWebhookDTO wallexWebhookDTO) throws JsonMappingException, JsonProcessingException, ParseException {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		ResponseEntity<ResponseModel> isWebhookAuthenticateRequest = wallexMiddleware
				.isWebhookAuthenticateRequest(allHeaders);
		if (isWebhookAuthenticateRequest.getStatusCodeValue() != HttpStatus.OK.value()) {
			logger.info("isWebhookAuthenticateRequest = " + isWebhookAuthenticateRequest.getBody().getErrorMessage());
			return isWebhookAuthenticateRequest;
		}
		ResponseEntity<ResponseModel> isWebhookValidRequest = wallexMiddleware.isWebhookValidRequest(wallexWebhookDTO);
		if (isWebhookValidRequest.getStatusCodeValue() != HttpStatus.OK.value()) {
			logger.info("isWebhookValidRequest = " + isWebhookValidRequest.getBody().getErrorMessage());
			return isWebhookValidRequest;
		}
		String res = "{\"accountId\":\"9d2580ff-7307-4c7d-930e-694209a01f12\",\"amount\":994.46,\"sender\":{\"name\":\"Abhinav\",\"accountNumber\":\"6413481382\"},\"collectionAccountId\":\"4ed677f6-9ebf-470d-870a-3d1b2031757d\",\"amountOrigin\":1000,\"fee\":5.54,\"currency\":\"GBP\",\"id\":\"c4ae6c31-64e2-4786-8142-fa17429ee722\",\"paymentDetails\":{},\"status\":\"completed\",\"creditedAt\":\"2022-06-28T11:55:37Z\",\"paymentType\":\"swift\"}";
		ResponseModel rm = new ResponseModel();
		JSONObject json =  new JSONObject(res);
		rm.setResult(wallexDataService.convertToWallexDepositResponse(json.toString()));
		return new ResponseEntity<>(rm, HttpStatus.OK);
//		return wallexDataService.callWebhook(allHeaders, wallexWebhookDTO);
	}
}
