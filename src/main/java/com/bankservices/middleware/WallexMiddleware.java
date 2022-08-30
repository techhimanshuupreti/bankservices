package com.bankservices.middleware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.util.StringUtils;
import com.bankservices.constants.ResponseMessage;
import com.bankservices.constants.WallexWebhookTypeConstants;
import com.bankservices.dto.WallexWebhookDTO;
import com.bankservices.models.ResponseModel;
import com.bankservices.requests.WallexUserSignupRequest;
import com.bankservices.utils.PropertiesManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WallexMiddleware implements MiddlewareInterface {

	private Logger logger = LoggerFactory.getLogger(WallexMiddleware.class.getName());

	@Autowired
	private PropertiesManager propertiesManager;

	@Override
	public ResponseEntity<ResponseModel> isWebhookValidRequest(Object validateReq) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {

			if (validateReq == null) {
				logger.info("Wallex User Signup Request is not valid.");
				ResponseModel responseModel = new ResponseModel();
				responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
				responseModel.setErrors(Arrays.asList("ResourceId","Resource","Status"));
				return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
			} else if (validateReq instanceof WallexWebhookDTO) {
				logger.info("Wallex Webhook Request is starting validating.");
				WallexWebhookDTO wwDto = (WallexWebhookDTO) validateReq;
				List<String> missingParam = new ArrayList<>();
				if (StringUtils.isNullOrEmpty(wwDto.getResourceId())) {
					logger.info("Wallex Webhook Request is not validating resource id.");
					missingParam.add("ResourceId");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getResource())) {
					logger.info("Wallex Webhook Request is not validating resource.");
					missingParam.add("Resource");
				}else if(!wwDto.getResource().equalsIgnoreCase(WallexWebhookTypeConstants.USER.getValue()) &&
						!wwDto.getResource().equalsIgnoreCase(WallexWebhookTypeConstants.COLLECTION.getValue()) &&
						!wwDto.getResource().equalsIgnoreCase(WallexWebhookTypeConstants.SIMPLE_PAYMENT.getValue())) {
					missingParam.add("Resource");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getStatus())) {
					logger.info("Wallex Webhook Request is not validating status.");
					missingParam.add("Status");
				}
				
				logger.info("Wallex Webhook Request, all validation success.");
				if(!missingParam.isEmpty()) {
					ResponseModel responseModel = new ResponseModel();
					responseModel.setErrorMessage(ResponseMessage.WALLEX_FEW_REQUEST_FIELDS_INVALID.getValue());
					responseModel.setErrors(missingParam);
					return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
				}
			}

			ResponseModel responseModel = new ResponseModel();
			responseModel.setResult(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_VALID.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Webhook Authenticate " + ex);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_FEW_REQUEST_FIELDS_INVALID.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<ResponseModel> isAuthenticateRequest(Object authenticateRequestObj) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		try {
			if (authenticateRequestObj == null) {
				logger.info("Webhook Request is not authenticate.");
				ResponseModel responseModel = new ResponseModel();
				responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
				return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
			} else if (authenticateRequestObj instanceof HashMap) {
				Map<String, String> reqMap = new ObjectMapper().readValue(authenticateRequestObj.toString(),
						HashMap.class);
				
//				ResponseModel responseModel = new ResponseModel();
//				responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
//				return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);

			}

			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_AUTH.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			logger.error("Webhook Authenticate " + ex);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<ResponseModel> isValidRequest(Object validateRequestObj) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		try {

			if (validateRequestObj == null) {
				logger.info("Wallex User Signup Request is not valid.");
				ResponseModel responseModel = new ResponseModel();
				responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
				responseModel.setErrors(Arrays.asList("Account type","Company name","Country code","Email","First name","Language","Last name",
						"Password"));
				return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
			} else if (validateRequestObj instanceof WallexUserSignupRequest) {
				logger.info("Wallex User Signup Request is starting validating.");
				WallexUserSignupRequest wwDto = (WallexUserSignupRequest) validateRequestObj;
				List<String> missingParam = new ArrayList<>();
				if (StringUtils.isNullOrEmpty(wwDto.getAccountType())) {
					logger.info("Wallex User Signup Request is not validating account type.");
					missingParam.add("Account type");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getCompanyName())) {
					logger.info("Wallex User Signup Request is not validating company name.");
					missingParam.add("Company name");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getCountryCode())) {
					logger.info("Wallex User Signup Request is not validating country code.");
					missingParam.add("Country code");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getEmail())) {
					logger.info("Wallex User Signup Request is not validating email.");
					missingParam.add("Email");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getFirstName())) {
					logger.info("Wallex User Signup Request is not validating first name.");
					missingParam.add("First name");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getLanguage())) {
					logger.info("Wallex User Signup Request is not validating language.");
					missingParam.add("Language");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getLastName())) {
					logger.info("Wallex User Signup Request is not validating last name.");
					missingParam.add("Last name");
				}
				if (StringUtils.isNullOrEmpty(wwDto.getPassword())) {
					logger.info("Wallex User Signup Request is not validating password.");
					missingParam.add("Password");
				}
				logger.info("Wallex User Signup Request, all validation success.");
				if(!missingParam.isEmpty()) {
					ResponseModel responseModel = new ResponseModel();
					responseModel.setErrorMessage(ResponseMessage.WALLEX_FEW_REQUEST_FIELDS_INVALID.getValue());
					responseModel.setErrors(missingParam);
					return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
				}
			}

			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_VALID.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Webhook Authenticate " + ex);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage("Email"+ResponseMessage.WALLEX_FEW_REQUEST_FIELDS_INVALID.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<ResponseModel> isWebhookAuthenticateRequest(Object authenticateRequestObj) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		try {
			if (authenticateRequestObj == null) {
				logger.info("Webhook Request is not authenticate.");
				ResponseModel responseModel = new ResponseModel();
				responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_EMPTY.getValue());
				return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
			} else if (authenticateRequestObj instanceof HashMap) {
				logger.info("Webhook Request Authenticate is Map type.");
				logger.info("Wallex Request is starting authenticate.");
				HashMap<String, String> reqMap = (HashMap) authenticateRequestObj;
				String x_api_key = propertiesManager.getSystemProperty("WALLEX_X_API_KEY_VA_PUSH");
				String header_x_ap_key = reqMap.get("x-api-key");
				if (!x_api_key.equalsIgnoreCase(header_x_ap_key)) {
					logger.info("Webhook Authenticate header is not equal.");
					logger.info("Wallex Request is not authenticate.");
					ResponseModel responseModel = new ResponseModel();
					responseModel.setErrorMessage(ResponseMessage.API_REQUEST_WRONG.getValue());
					return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
				}
			}
			logger.info("Wallex Request, all authenticated.");
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_ALL_REQUEST_FIELDS_AUTH.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("no Webhook Authenticate " + ex);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMessage(ResponseMessage.WALLEX_FEW_REQUEST_FIELDS_INVALID.getValue());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

}
