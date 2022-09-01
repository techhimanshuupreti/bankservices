package com.bankservices.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.StringUtils;
import com.bankservices.constants.ResponseMessage;
import com.bankservices.constants.WallexStatusType;
import com.bankservices.constants.WallexUrlContant;
import com.bankservices.constants.WallexWebhookTypeConstants;
import com.bankservices.dto.WallexWebhookDTO;
import com.bankservices.entities.WallexAPI;
import com.bankservices.middleware.WallexMiddleware;
import com.bankservices.models.ResponseModel;
import com.bankservices.requests.WallexUserPersonalDetailsRequest;
import com.bankservices.requests.WallexUserSignupRequest;
import com.bankservices.requests.WallexOnbaordDocumentUploadRequest;
import com.bankservices.requests.WallexUserBusinessDetailsRequest;
import com.bankservices.requests.WallexUserOnboardingDocumentsRequest;
import com.bankservices.requests.WallexUserOnboardingKycRequest;
import com.bankservices.response.WalleUserSignupResponse;
import com.bankservices.response.WallexDepositResponse;
import com.bankservices.respositories.WallexApiRespository;
import com.bankservices.utils.CommonConstants;
import com.bankservices.utils.DocumentType;
import com.bankservices.utils.ErrorMapping;
import com.bankservices.utils.PropertiesManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class WallexDataServices {

	private Logger logger = LoggerFactory.getLogger(WallexDataServices.class);

	@Autowired
	private PropertiesManager propertiesManager;

	@Autowired
	private WallexApiRespository wallexApiRespository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private WallexMiddleware wallexMiddleware;

	private RestTemplate restTemplate;

	public WallexDataServices() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * This will generate token to authenciate the wallex requests.
	 */
	public ResponseEntity<ResponseModel> generateWallexApiToken() {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String tokenUrl = "";
		String request = "";
		String response = "";
		try {
			tokenUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_TOKEN_URL.getValue());

			Map<String, String> mapTokenPayload = new HashMap<String, String>();
			mapTokenPayload.put("accessKeyId",
					propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_ACCESS_KEY_ID.getValue()));
			mapTokenPayload.put("secretAccessKey",
					propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_SECRET_ACCESS_KEY.getValue()));

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-api-key", propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));

			HttpEntity<?> headersPayloadEntity = new HttpEntity<Object>(mapTokenPayload, headers);

			ResponseEntity<String> tokenApiResponse = restTemplate.postForEntity(tokenUrl, headersPayloadEntity,
					String.class);

			System.out.println(tokenApiResponse.getStatusCode());
			System.out.println(tokenApiResponse.getStatusCode().name());
			System.out.println(tokenApiResponse.getStatusCode().value());
			JSONObject jsonObj = new JSONObject(tokenApiResponse.getBody());

			request = (headersPayloadEntity.getBody().toString()).trim();
			response = tokenApiResponse.getBody();
			saveApiCallInDatabase(tokenUrl, request, response, tokenApiResponse.getStatusCodeValue());

			if (tokenApiResponse.getStatusCode() != null
					&& tokenApiResponse.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info("token api has success response -> " + tokenApiResponse.getBody() + " , code "
						+ tokenApiResponse.getStatusCode());
				ResponseModel responseModel = new ResponseModel();
				responseModel.setResult(jsonObj.getString("token"));
				responseModel.setErrorMessage("");
				responseModel.setErrors(new ArrayList<>(Arrays.asList()));
				return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
			}
			logger.info("token api has unsuccess response -> " + tokenApiResponse.getBody() + " , code "
					+ tokenApiResponse.getStatusCode());
			return mappedErrorWithResponseModal(tokenApiResponse.getBody(), tokenApiResponse.getStatusCode(),
					Arrays.asList(), "");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getStatusText());
			saveApiCallInDatabase(tokenUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			if (httpClentErrorEx.getStatusText().equalsIgnoreCase(HttpStatus.FORBIDDEN.name())) {
				return mappedErrorWithResponseModal(ResponseMessage.API_REQUEST_WRONG.getValue(),
						httpClentErrorEx.getStatusCode(), Arrays.asList(), "Exception");
			} else {
				return mappedErrorWithResponseModal(httpClentErrorEx.getStatusText(), httpClentErrorEx.getStatusCode(),
						Arrays.asList(), "Exception");
			}
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.API_TOKEN_NOT_GENERATE.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	/**
	 * API will registered to User.
	 */
	public ResponseEntity<ResponseModel> signup(WallexUserSignupRequest walleUserSignupRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String signupUrl = "";
		String request = "";
		String response = "";
		try {
			signupUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_SIGNUP_URL.getValue());

			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				Map<String, String> requestParam = new HashMap<String, String>();
				requestParam.put("email", walleUserSignupRequest.getEmail());
				requestParam.put("firstName", walleUserSignupRequest.getFirstName());
				requestParam.put("lastName", walleUserSignupRequest.getLastName());
				requestParam.put("password", walleUserSignupRequest.getPassword());
				requestParam.put("language", walleUserSignupRequest.getLanguage());
				requestParam.put("countryCode", walleUserSignupRequest.getCountryCode());
				requestParam.put("accountType", walleUserSignupRequest.getAccountType());
				requestParam.put("companyName", walleUserSignupRequest.getCompanyName());

				HttpEntity<?> headersPayloadEntity = new HttpEntity<Object>(requestParam, headers);
				request = (headersPayloadEntity.getBody().toString()).trim();

				ResponseEntity<String> signupApiResponse = restTemplate.postForEntity(signupUrl, headersPayloadEntity,
						String.class);

				logger.info("Wallex User Signup Response :- " + response);
				response = signupApiResponse.getBody();
				saveApiCallInDatabase(signupUrl, request, response, signupApiResponse.getStatusCodeValue());

				if (signupApiResponse.getStatusCode() != null
						&& signupApiResponse.getStatusCode().value() == HttpStatus.OK.value()) {
					logger.info("user signup wallex api status success and response. " + signupApiResponse.getBody());
					ResponseModel responseModal = new ResponseModel();
					responseModal.setResult(convertToWallexUserSignupResponse(signupApiResponse.getBody()));
					responseModal.setErrorMessage("");
					responseModal.setErrors(new ArrayList<>());
					return new ResponseEntity<ResponseModel>(responseModal, HttpStatus.OK);
				} else {
					logger.info("user signup wallex api status unsuccess and response. " + signupApiResponse.getBody());
					return mappedErrorWithResponseModal(signupApiResponse.getBody(), signupApiResponse.getStatusCode(),
							Arrays.asList(), "");
				}
			}
			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					Arrays.asList(), "internal");

		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(signupUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_USER_NOT_REGISTERED_IN_WALLEX.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	/*
	 * This will update personal details of User.
	 */
	public ResponseEntity<ResponseModel> updateUserPersonalDetails(
			WallexUserPersonalDetailsRequest wallexUserPersonalDetailsRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String personalDetailsUrl = "";
		String request = "";
		String response = "";
		try {
			personalDetailsUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_USER_URL.getValue());
			personalDetailsUrl += wallexUserPersonalDetailsRequest.getWallexUserId() + "/detail";
			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				Map<String, String> requestParam = new HashMap<String, String>();
				requestParam.put("mobileCountryCode", wallexUserPersonalDetailsRequest.getMobileCountryCode());
				requestParam.put("mobileNumber", wallexUserPersonalDetailsRequest.getMobileNumber());
				requestParam.put("gender", wallexUserPersonalDetailsRequest.getGender());
				requestParam.put("countryOfBirth", wallexUserPersonalDetailsRequest.getCountryOfBirth());
				requestParam.put("nationality", wallexUserPersonalDetailsRequest.getNationality());
				requestParam.put("residentialAddress", wallexUserPersonalDetailsRequest.getResidentialAddress());
				requestParam.put("city", wallexUserPersonalDetailsRequest.getCity());
				requestParam.put("postalCode", wallexUserPersonalDetailsRequest.getPostalCode());
				requestParam.put("dateOfBirth", wallexUserPersonalDetailsRequest.getDateOfBirth());
				requestParam.put("identificationType", wallexUserPersonalDetailsRequest.getIdentificationType());
				requestParam.put("identificationNumber", wallexUserPersonalDetailsRequest.getIdentificationNumber());
				requestParam.put("issueDate", wallexUserPersonalDetailsRequest.getIssueDate());
				requestParam.put("expiryDate", wallexUserPersonalDetailsRequest.getExpiryDate());
				requestParam.put("employmentIndustry", wallexUserPersonalDetailsRequest.getEmploymentIndustry());
				requestParam.put("employmentStatus", wallexUserPersonalDetailsRequest.getEmploymentStatus());
				requestParam.put("employmentPosition", wallexUserPersonalDetailsRequest.getEmploymentPosition());

				HttpEntity<?> headersPayloadEntity = new HttpEntity<Object>(requestParam, headers);
				request = (headersPayloadEntity.getBody().toString()).trim();

				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setConnectTimeout(5000);
				requestFactory.setReadTimeout(10000);

				restTemplate.setRequestFactory(requestFactory);
				ResponseEntity<String> personalDetailsApiResponse = restTemplate.exchange(personalDetailsUrl,
						HttpMethod.PATCH, headersPayloadEntity, String.class);
				logger.info("Wallex User update Personal Details Response :- " + personalDetailsApiResponse);
				response = personalDetailsApiResponse.getBody();

				if (personalDetailsApiResponse.getStatusCode() != null
						&& personalDetailsApiResponse.getStatusCode().value() == HttpStatus.OK.value()) {
					logger.info("wallex update User Personal Details. status success and response. "
							+ personalDetailsApiResponse.getBody());
					JSONObject checkingMissingFields = new JSONObject(personalDetailsApiResponse.getBody());

					if (checkingMissingFields.has("status") && checkingMissingFields.getJSONObject("status")
							.getString("value").equalsIgnoreCase("completed")) {
						logger.info("wallex update User Personal Details. status of user fields "
								+ checkingMissingFields.getJSONObject("status").getString("value"));
						saveApiCallInDatabase(personalDetailsUrl, request, response,
								personalDetailsApiResponse.getStatusCodeValue());
						ResponseModel responseModel = new ResponseModel();
						responseModel.setErrorMessage("");
						responseModel.setErrors(null);
						checkingMissingFields.remove("status");
						logger.info("personalDetailsApiResponse remove = " + checkingMissingFields);
						responseModel.setResult(convertToWallexUserSignupResponse(checkingMissingFields.toString()));
						return new ResponseEntity<ResponseModel>(responseModel,
								personalDetailsApiResponse.getStatusCode());

					} else {
						logger.info("wallex update User Personal Details. status of user fields "
								+ checkingMissingFields.getJSONObject("status").getString("value"));
						saveApiCallInDatabase(personalDetailsUrl, request, response,
								personalDetailsApiResponse.getStatusCodeValue());
						return mappedErrorWithResponseModal(checkingMissingFields.getJSONObject("status").toString(),
								personalDetailsApiResponse.getStatusCode(), Arrays.asList(), "");
					}

				}
				saveApiCallInDatabase(personalDetailsUrl, request, response,
						personalDetailsApiResponse.getStatusCodeValue());
				return mappedErrorWithResponseModal(personalDetailsApiResponse.getBody(),
						personalDetailsApiResponse.getStatusCode(), Arrays.asList(), "");

			}
			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					token.getBody().getErrors(), "internal");

		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(personalDetailsUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_USER_NOT_REGISTERED_IN_WALLEX.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	public ResponseEntity<ResponseModel> updateUserBusinessDetails(
			WallexUserBusinessDetailsRequest wallexUserBusinessDetailsRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String businessDetailsUrl = "";
		String request = "";
		String response = "";
		try {
			businessDetailsUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_USER_URL.getValue());

			businessDetailsUrl += wallexUserBusinessDetailsRequest.getWallexUserId() + "/company";

			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				Map<String, String> requestParam = new HashMap<String, String>();
				requestParam.put("countryOfIncorporation",
						wallexUserBusinessDetailsRequest.getCountryOfIncorporation());
				requestParam.put("countryOfOperations", wallexUserBusinessDetailsRequest.getCountryOfOperations());
				requestParam.put("businessType", wallexUserBusinessDetailsRequest.getBusinessType());
				requestParam.put("companyAddress", wallexUserBusinessDetailsRequest.getBusinessAddress());
				requestParam.put("postalCode", wallexUserBusinessDetailsRequest.getBusinessPinCode());
				requestParam.put("state", wallexUserBusinessDetailsRequest.getBusinessState());
				requestParam.put("city", wallexUserBusinessDetailsRequest.getBusinessCity());
				requestParam.put("registrationNumber", wallexUserBusinessDetailsRequest.getRegistrationNumber());
				requestParam.put("incorporationDate", wallexUserBusinessDetailsRequest.getIncorporationDate());

				HttpEntity<?> headersPayloadEntity = new HttpEntity<Object>(requestParam, headers);
				request = (headersPayloadEntity.getBody().toString()).trim();

				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setConnectTimeout(5000);
				requestFactory.setReadTimeout(10000);

				restTemplate.setRequestFactory(requestFactory);
				ResponseEntity<String> businessDetailsApiResponse = restTemplate.exchange(businessDetailsUrl,
						HttpMethod.PATCH, headersPayloadEntity, String.class);
				logger.info("Wallex User update Business Details Response :- " + businessDetailsApiResponse);
				response = businessDetailsApiResponse.getBody();

				if (businessDetailsApiResponse.getStatusCode() != null
						&& businessDetailsApiResponse.getStatusCode().value() == HttpStatus.OK.value()) {
					logger.info("wallex update User Business Details. status success and response. "
							+ businessDetailsApiResponse.getBody());
					JSONObject checkingMissingFields = new JSONObject(businessDetailsApiResponse.getBody());

					if (checkingMissingFields.has("status") && checkingMissingFields.getJSONObject("status")
							.getString("value").equalsIgnoreCase("completed")) {
						logger.info("wallex update User Business Details. status of user fields "
								+ checkingMissingFields.getJSONObject("status").getString("value"));

						saveApiCallInDatabase(businessDetailsUrl, request, response,
								businessDetailsApiResponse.getStatusCodeValue());

						ResponseModel responseModel = new ResponseModel();
						responseModel.setErrorMessage("");
						responseModel.setErrors(null);
						checkingMissingFields.remove("status");
						logger.info("businessDetailsApiResponse remove = " + checkingMissingFields);
						responseModel.setResult(convertToWallexUserSignupResponse(checkingMissingFields.toString()));
						return new ResponseEntity<ResponseModel>(responseModel,
								businessDetailsApiResponse.getStatusCode());

					} else {
						logger.info("wallex update User Business Details. status of user fields "
								+ checkingMissingFields.getJSONObject("status").getString("value"));
						saveApiCallInDatabase(businessDetailsUrl, request, response,
								businessDetailsApiResponse.getStatusCodeValue());
						return mappedErrorWithResponseModal(checkingMissingFields.getJSONObject("status").toString(),
								businessDetailsApiResponse.getStatusCode(), Arrays.asList(), "");
					}
				}
				saveApiCallInDatabase(businessDetailsUrl, request, response,
						businessDetailsApiResponse.getStatusCodeValue());
				return mappedErrorWithResponseModal(businessDetailsApiResponse.getBody(),
						businessDetailsApiResponse.getStatusCode(), Arrays.asList(), "");
			}

			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					token.getBody().getErrors(), "internal");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(businessDetailsUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_USER_NOT_REGISTERED_IN_WALLEX.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	/**
	 * This will upload merchant onboarding document in wallex.
	 */
	public ResponseEntity<ResponseModel> uploadMerchantDocuments(
			WallexUserOnboardingDocumentsRequest wallexUserOnboardingDocumentsResuest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {
			ResponseEntity<ResponseModel> responseModal = createDocument(
					wallexUserOnboardingDocumentsResuest.getWallexUserId(),
					wallexUserOnboardingDocumentsResuest.getMerchantId(),
					wallexUserOnboardingDocumentsResuest.getOnboardDocs());

			logger.info("uploadMerchantDocuments api response -> " + responseModal.getBody() + " , code "
					+ responseModal.getStatusCode());

			if (responseModal.getStatusCodeValue() == HttpStatus.OK.value()) {
				logger.info("Status = " + responseModal.getStatusCodeValue());
				ResponseModel responseModel = new ResponseModel();
				responseModel.setResult(ResponseMessage.WALLEX_DOC_UPLOAD.getValue());
				return new ResponseEntity<>(responseModel, HttpStatus.OK);
			}

			return mappedErrorWithResponseModal(responseModal.getBody().getErrorMessage(),
					responseModal.getStatusCode(), responseModal.getBody().getErrors(), "internal");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_DOC_NOT_UPLOAD.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	/**
	 * This will call from uploadMerchantDocuments.
	 */
	@SuppressWarnings("resource")
	public ResponseEntity<ResponseModel> createDocument(String wallexUserId, String merchantId,
			List<WallexOnbaordDocumentUploadRequest> wallexOnbaordDocumentUploadRequests) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String uploadOnboardingDocsUrl = "";

		String outRequest = "";
		List<WallexOnbaordDocumentUploadRequest> reposneDetails = new ArrayList<>();
		try {
			uploadOnboardingDocsUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_USER_URL.getValue());

			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());

				uploadOnboardingDocsUrl += wallexUserId + "/documents";
				boolean isSuccess = false;
				for (WallexOnbaordDocumentUploadRequest docUploadData : wallexOnbaordDocumentUploadRequests) {
					String request = "";
					String response = "";

					Map<String, String> requestParam = new HashMap<>();
					requestParam.put("documentType", docUploadData.getDocumentType().toLowerCase());
					requestParam.put("documentName", docUploadData.getDocumentName().toLowerCase());

					HttpHeaders headers = new HttpHeaders();
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.set("x-api-key",
							propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
					headers.set("Authorization", token.getBody().getResult().toString());

					HttpEntity<?> headersPayloadEntity = new HttpEntity<>(requestParam, headers);
					request = (headersPayloadEntity.getBody().toString()).trim();

					ResponseEntity<String> uploadOnboardingDocsResponse = restTemplate
							.postForEntity(uploadOnboardingDocsUrl, headersPayloadEntity, String.class);
					logger.info("Create Document Response with WallexUserId and EmailId :- " + wallexUserId + " , "
							+ uploadOnboardingDocsResponse);

					response = uploadOnboardingDocsResponse.getBody();
					saveApiCallInDatabase(uploadOnboardingDocsUrl, request, response,
							uploadOnboardingDocsResponse.getStatusCodeValue());
					if (uploadOnboardingDocsResponse.getStatusCode() != null
							&& uploadOnboardingDocsResponse.getStatusCode().value() == HttpStatus.OK.value()) {

						logger.info("wallex update User createDocument. status success and response. "
								+ uploadOnboardingDocsResponse.getBody());
						JSONObject responseMapping = new JSONObject(uploadOnboardingDocsResponse.getBody());

						if (responseMapping.has("id")) {
							docUploadData.setWallexDocumentId(responseMapping.getString("id"));
						} else {
							docUploadData.setWallexDocumentId("NA");
						}

						if (responseMapping.has("documentName")) {
							if (!responseMapping.getString("documentName")
									.equalsIgnoreCase(docUploadData.getDocumentType().toLowerCase())) {
								docUploadData.setDocumentName(responseMapping.getString("documentName"));
							}
						}

						if (responseMapping.has("documentType")) {
							if (!responseMapping.getString("documentType")
									.equalsIgnoreCase(docUploadData.getDocumentType().toLowerCase())) {
								docUploadData.setDocumentType(responseMapping.getString("documentType"));
							}
						}

						if (responseMapping.has("uploadURL")) {
							docUploadData.setWallexDocumentUploadUrl(responseMapping.getString("uploadURL"));
						} else {
							docUploadData.setWallexDocumentUploadUrl("NA");
						}
						logger.info("after setting response in pojo class in wallex doc upload, " + docUploadData);

						// download the file mapped into file object
						String newFolderName = merchantId;
						String parentFolder = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MAIN_DOCUMENT_FOLDER");
						String merOnboardingDoc = propertiesManager
								.getSystemProperty("AWS_S3_BUCKET_MERCHANT_ONBOARDIND_DOCUMENT_FOLDER");
						logger.info("downloadFile doc for aws s3 bucket=" + parentFolder + merOnboardingDoc
								+ newFolderName + "/" + docUploadData.getDocumentName());
						byte[] data = storageService.downloadFile(docUploadData.getDocumentName(),
								parentFolder + merOnboardingDoc + newFolderName);

						// Try block to check for exceptions
						try {

							// Initialize a pointer in file
							// using OutputStream
							String extension = docUploadData.getDocumentName().split("\\.", 2)[1];
							logger.info("extension = " + extension);
							logger.info(
									"Modified FIleName  = " + docUploadData.getDocumentReference() + "." + extension);
							String filName = docUploadData.getDocumentReference() + "." + extension;
							File file = new File(filName);
							FileOutputStream os = new FileOutputStream(file);

							// Starting writing the bytes in it
							os.write(data);

							// Display message on console for successful execution
							logger.info("Successfully byte inserted");

//							String uplaodDocInWallex = uploadDocument(docUploadData.getWallexDocumentUploadUrl(), file);
							ResponseEntity<ResponseModel> uplaodDocInWallex = uploadDocument(
									docUploadData.getWallexDocumentUploadUrl(), file);
							if (uplaodDocInWallex.getStatusCodeValue() == HttpStatus.OK.value()) {
								logger.info("upload docs in wallex success, " + uplaodDocInWallex);
								docUploadData.setWallexDocumentDownloadUrl(
										uplaodDocInWallex.getBody().getResult().toString());
								isSuccess = true;
							} else {
								logger.error("Documents are uploaded unsuccessfully.");
								return mappedErrorWithResponseModal(ResponseMessage.WALLEX_DOC_NOT_UPLOAD.getValue(),
										HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
							}

							// Close the file connections
							os.close();
						} catch (Exception e) {
							logger.error("Error -> " + e);
							return mappedErrorWithResponseModal(ResponseMessage.WALLEX_DOC_NOT_UPLOAD.getValue(),
									HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
						}
					} else {
						logger.error("Documents are uploaded unsuccessfully.");
						return mappedErrorWithResponseModal(uploadOnboardingDocsResponse.getBody(),
								uploadOnboardingDocsResponse.getStatusCode(), Arrays.asList(), "internal");
					}
					reposneDetails.add(docUploadData);
				}
				if (!reposneDetails.isEmpty() && isSuccess) {
					logger.info("reposneDetails = " + reposneDetails);
					ResponseModel responseModel = new ResponseModel();
					responseModel.setResult(reposneDetails);
					return new ResponseEntity<>(responseModel, HttpStatus.OK);
				}
			}

			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					Arrays.asList(), "internal");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(uploadOnboardingDocsUrl, outRequest, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_DOC_NOT_UPLOAD.getValue(),
					HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	@SuppressWarnings("deprecation")
	public ResponseEntity<ResponseModel> uploadDocument(String url, File file) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {
			logger.info("File Name=" + file.getName());
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA));

			String extension = FilenameUtils.getExtension(file.getName());
			String mediaTypeStr = "";
			if (extension.equalsIgnoreCase("pdf")) {
				headers.setContentType(MediaType.APPLICATION_PDF);
				logger.info("content-type=" + extension);
				mediaTypeStr = MediaType.APPLICATION_PDF_VALUE;
			} else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
				logger.info("content-type=" + extension);
				mediaTypeStr = MediaType.IMAGE_JPEG_VALUE;
			} else if (extension.equalsIgnoreCase("png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
				logger.info("content-type=" + extension);
				mediaTypeStr = MediaType.IMAGE_PNG_VALUE;
			}
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			okhttp3.MediaType mediaType = okhttp3.MediaType.parse(mediaTypeStr);
			RequestBody body = RequestBody.create(mediaType, file);

			Request request = new Request.Builder().url(url).method("PUT", body)
					.addHeader("x-amz-storage-class", "STANDARD").addHeader("Content-Type", mediaTypeStr)
					.addHeader("x-api-key",
							propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()))
					.build();
			Response responseData = client.newCall(request).execute();
			logger.info("Upload Document with URL :- " + url + " and Response :- " + responseData);

			saveApiCallInDatabase(url, request.body().toString(), responseData.toString(), HttpStatus.OK.value());
//			return CommonConstants.SUCCESS.name();
			ResponseModel responseModel = new ResponseModel();
			responseModel.setResult(responseData.toString());
			return new ResponseEntity<>(responseModel, HttpStatus.OK);

		} catch (Exception e) {
			logger.info("Error while validating Kyc Screen on Wallex :- " + e.getMessage());
//			return CommonConstants.ERROR.name();
			ResponseModel responseModel = new ResponseModel();
			responseModel.setResult(CommonConstants.ERROR.name());
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This will call after all wallex api of user register. It will give final
	 * status of user registration .
	 */
	public ResponseEntity<ResponseModel> kycScreen(WallexUserOnboardingKycRequest wallexUserOnboardingKycRequest) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		String kycScreenUrl = "";
		String request = "";
		String response = "";
		try {
			kycScreenUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_USER_URL.getValue());

			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());

				kycScreenUrl += wallexUserOnboardingKycRequest.getWallexUserId() + "/screen";

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				HttpEntity<?> entity = new HttpEntity<>(headers);

				ResponseEntity<String> kycScreenResponse = restTemplate.postForEntity(kycScreenUrl, entity,
						String.class);

				logger.info("Signup Response :- " + kycScreenResponse);

				response = kycScreenResponse.getBody();
				saveApiCallInDatabase(kycScreenUrl, request, response, kycScreenResponse.getStatusCodeValue());

				if (kycScreenResponse.getStatusCode() != null
						&& kycScreenResponse.getStatusCode().value() == HttpStatus.OK.value()) {
					logger.info("Status = " + kycScreenResponse.getStatusCodeValue());
					ResponseModel responseModel = new ResponseModel();
					responseModel.setResult(kycScreenResponse.getBody());
					return new ResponseEntity<>(responseModel, HttpStatus.OK);
				} else {
					logger.info("token api has unsuccess response -> " + token.getBody() + " , code "
							+ token.getStatusCode());
					return mappedErrorWithResponseModal(kycScreenResponse.getBody(), kycScreenResponse.getStatusCode(),
							Arrays.asList(), "internal");
				}
			}

			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					token.getBody().getErrors(), "internal");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(kycScreenUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_KYC_NOT_DONE.getValue(), HttpStatus.BAD_REQUEST,
					Arrays.asList(), "Exception");
		}
	}

	/**
	 * This will save wallex api response in database
	 */
	public void saveApiCallInDatabase(String apiUrl, String apiRequest, String apiResponse, int statusCode) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {
			WallexAPI waApi = new WallexAPI();
			waApi.setApiRequest(apiRequest);
			waApi.setApiRespons(apiResponse);
			waApi.setStatusCode(statusCode);
			waApi.setCreatedAt(Calendar.getInstance().getTime());
			waApi.setApiEndpoint(apiUrl);
			wallexApiRespository.save(waApi);
		} catch (Exception e) {
			logger.error("Error -> " + e);
		}
	}

	// mapping between signup api response to api Wallex User Signup Response
	public WalleUserSignupResponse convertToWallexUserSignupResponse(String responseData) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			WalleUserSignupResponse walleUserSignupRes = objectMapper.readValue(responseData,
					WalleUserSignupResponse.class);
			return walleUserSignupRes;
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return new WalleUserSignupResponse();
		}
	}

	/**
	 * handle to API Error
	 */
	public ResponseEntity<ResponseModel> mappedErrorWithResponseModal(String error, HttpStatus statusCode,
			List<String> errors, String type) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		ResponseModel responseModal = new ResponseModel();
		responseModal.setResult("");

		if (type.equalsIgnoreCase("Exception") || type.equalsIgnoreCase("internal") && errors.isEmpty()) {
			responseModal.setErrorMessage(error);
			responseModal.setErrors(Arrays.asList());
		} else if ((type.equalsIgnoreCase("Exception") || type.equalsIgnoreCase("internal")) && !errors.isEmpty()) {
			responseModal.setErrorMessage(error);
			responseModal.setErrors(errors);
		} else {
			ErrorMapping errorMapping = mappingError(error);
			responseModal.setErrorMessage(errorMapping.getMessage());
			responseModal.setErrors(errorMapping.getErrors());
		}
		return new ResponseEntity<ResponseModel>(responseModal, statusCode);
	}

	/**
	 * This will map to wallex errors.
	 */
	@SuppressWarnings("unchecked")
	public ErrorMapping mappingError(String errorResponse) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {
			JSONObject errorJSON = new JSONObject(errorResponse);
			ErrorMapping errorMapping = new ErrorMapping();

			if (errorJSON.has("message")) {
				if (errorJSON.getString("message").toLowerCase().contains("invalid")
						|| errorJSON.getString("message").toLowerCase().contains("at least")
						|| errorJSON.getString("message").toLowerCase().contains("special character")
						|| errorJSON.getString("message").toLowerCase().contains("missing")) {
					errorMapping.setMessage(ResponseMessage.FIELDS_INVALID_MISSING.getValue());
				} else {
					errorMapping.setMessage(errorJSON.getString("message"));
				}
			}

			if (errorJSON.has("value")) {
				errorMapping.setMessage(errorJSON.getString("value"));
			}

			if (errorJSON.has("errors")) {
				JSONArray errorArr = errorJSON.getJSONArray("errors");
				if (!errorJSON.isNull(errorResponse) || !errorJSON.isEmpty()) {
					List<String> errorsList = new ArrayList<>();
					for (int i = 0; i < errorArr.length(); i++) {
						JSONObject json = errorArr.getJSONObject(i);

						if (json.get("message") instanceof JSONArray) {
							JSONArray msgArrTypeError = (JSONArray) json.get("message");
							for (Object obj : msgArrTypeError) {
								errorsList.add(obj.toString());
							}
						} else if (json.get("message") instanceof JSONObject) {
							HashMap<String, Object> result = new ObjectMapper()
									.readValue(json.get("message").toString(), HashMap.class);
							logger.info("result = " + result.values().stream().collect(Collectors.toList()));

							for (Entry<String, Object> map : result.entrySet()) {
								if (map.getValue() instanceof ArrayList) {
									List<String> listStr = (List<String>) map.getValue();
									for (String ss : listStr) {
										errorsList.add(ss);
									}
								}
								if (map.getValue() instanceof String) {
									errorsList.add(map.getValue().toString());
								}
							}
						} else {
							errorsList.add(json.get("message").toString());
						}
					}
					if (!errorsList.isEmpty()) {
						errorMapping.setErrors(errorsList);
					}
				}
			}

			if (errorJSON.has("missingFields")) {
				JSONArray errorArr = errorJSON.getJSONArray("missingFields");
				if (!errorJSON.isNull(errorResponse) || !errorJSON.isEmpty()) {
					List<String> errorsList = new ArrayList<>();
					for (int i = 0; i < errorArr.length(); i++) {
						errorsList.add(errorArr.get(i).toString());
					}
					if (!errorsList.isEmpty()) {
						errorMapping.setErrors(errorsList);
					}
				}
			}
			logger.info("errorMapping = >  " + errorMapping);
			return errorMapping;
		} catch (Exception e) {
			logger.error("error -> " + e);
			return new ErrorMapping();
		}
	}

	// start Webhook methods
	public ResponseEntity<ResponseModel> callWebhook(Map<String, String> allHeaders,
			WallexWebhookDTO wallexWebhookDTO) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		try {

			if (wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.USER.getValue())) {
				return getUserByResourceId(wallexWebhookDTO);
			} else if (wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.COLLECTION.getValue())) {
				return getCollectionByResourceId(wallexWebhookDTO);
			} else if (wallexWebhookDTO.getResource().equals(WallexWebhookTypeConstants.SIMPLE_PAYMENT.getValue())) {
				return getTransferByResourceId(wallexWebhookDTO);
			}
			return null;
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(e.getMessage(), HttpStatus.BAD_REQUEST, Arrays.asList(), "Exception");
		}
	}

	/**
	 * This will use for check status of user from wallex.
	 */
	public ResponseEntity<ResponseModel> getUserByResourceId(WallexWebhookDTO wallexWebhookDTO) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String webhookUserUrl = "";
		String request = "";
		String response = "";
		try {
			webhookUserUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_USER_URL.getValue());
			webhookUserUrl += wallexWebhookDTO.getResourceId();

			ResponseEntity<ResponseModel> token = generateWallexApiToken();

			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info(
						"token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				HttpEntity<?> entity = new HttpEntity<Object>(headers);
				ResponseEntity<String> responseWebhookUser = restTemplate.exchange(webhookUserUrl, HttpMethod.GET,
						entity, String.class);
				logger.info("User Response by resource Id :- " + responseWebhookUser);
				response = responseWebhookUser.getBody().toString();
				saveApiCallInDatabase(webhookUserUrl, request, response, responseWebhookUser.getStatusCodeValue());

				if (responseWebhookUser.getStatusCode() != null
						&& responseWebhookUser.getStatusCode().value() == HttpStatus.OK.value()) {
					JSONObject jsonObj = new JSONObject(responseWebhookUser.getBody());
					logger.info("responseWebhookUser :- " + jsonObj.toString());
					ResponseModel responseModal = new ResponseModel();
					responseModal.setResult(convertToWallexUserSignupResponse(responseWebhookUser.getBody()));
					responseModal.setErrorMessage("");
					responseModal.setErrors(new ArrayList<>());
					return new ResponseEntity<ResponseModel>(responseModal, HttpStatus.OK);
				} else {
					logger.info(
							"user signup wallex api status unsuccess and response. " + responseWebhookUser.getBody());
					return mappedErrorWithResponseModal(responseWebhookUser.getBody(),
							responseWebhookUser.getStatusCode(), Arrays.asList(), "");
				}
			}

			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					Arrays.asList(), "internal");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(webhookUserUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_KYC_NOT_DONE.getValue(), HttpStatus.BAD_REQUEST,
					Arrays.asList(), "Exception");
		}
	}
	
	/**
	 * This will use for transfer status from wallex.
	 */
	public ResponseEntity<ResponseModel> getTransferByResourceId(WallexWebhookDTO wallexWebhookDTO) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());

		String webhookTransferUrl = "";
		String request = "";
		String response = "";
		try {
			webhookTransferUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_TRANSFER_URL.getValue());
			webhookTransferUrl += wallexWebhookDTO.getResourceId();
//			webhookUserUrl += "?onBehalfOfAccount=" + transfer.getMerchant().getWallexAccountId(); ??
			
			ResponseEntity<ResponseModel> token = generateWallexApiToken();
			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				logger.info("token api has success response -> " + token.getBody() + " , code " + token.getStatusCode());
			
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("x-api-key",
						propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_X_API_KEY.getValue()));
				headers.set("Authorization", token.getBody().getResult().toString());

				HttpEntity<?> entity = new HttpEntity<Object>(headers);

				ResponseEntity<String> transferResponse = restTemplate.exchange(webhookTransferUrl, HttpMethod.GET, entity, String.class);

				logger.info("Collection Response by resource Id :- " + transferResponse);
				
				
				if(transferResponse.getStatusCodeValue() == HttpStatus.OK.value()) {
					logger.info("Success transfer webhook");
					ResponseModel rm = new ResponseModel();
					rm.setResult(transferResponse.getBody());
					new ResponseEntity<>(rm, HttpStatus.OK);
				}else {
					logger.info("Unsuccess transfer webhook");
					return mappedErrorWithResponseModal(transferResponse.getBody(), transferResponse.getStatusCode(),
							Arrays.asList(), "internal");
				}
			}
			
			
			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					Arrays.asList(), "internal");
		} catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(webhookTransferUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_KYC_NOT_DONE.getValue(), HttpStatus.BAD_REQUEST,
					Arrays.asList(), "Exception");
		}
	}
	
	/**
	 * This webhook is used for deposit.
	 * */
	private ResponseEntity<ResponseModel> getCollectionByResourceId(WallexWebhookDTO wallexWebhookDTO) {
		logger.info("InSide Class Name :- " + getClass() + ", Method :- "
				+ new Throwable().getStackTrace()[0].getMethodName());
		String webhookCollectionUrl = "";
		String request = "";
		String response = "";
		try {
			webhookCollectionUrl = propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_BASE_URL.getValue())
					+ propertiesManager.getSystemProperty(WallexUrlContant.WALLEX_GET_COLLECTION_URL.getValue());
			webhookCollectionUrl += wallexWebhookDTO.getResourceId();
			
			ResponseEntity<ResponseModel> token = generateWallexApiToken();
			if (token != null && token.getStatusCode().value() == HttpStatus.OK.value()) {
				
			logger.info("token api has unsuccess response -> " + token.getBody() + " , code " + token.getStatusCode());
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-api-key",propertiesManager
									.getSystemProperty(
											WallexUrlContant.WALLEX_X_API_KEY.getValue()));
			
			headers.set("Authorization", token.getBody().getResult().toString());

			HttpEntity<?> entity = new HttpEntity<Object>(headers);

			ResponseEntity<String> collectionResponse = restTemplate.exchange(webhookCollectionUrl, HttpMethod.GET, entity, String.class);

			logger.info("Collection Response by resource Id :- " + collectionResponse);
			if(collectionResponse.getStatusCodeValue() == HttpStatus.OK.value()) {
				logger.info("Success transfer webhook");
				ResponseModel rm = new ResponseModel();
				rm.setResult(convertToWallexDepositResponse(collectionResponse.getBody()));
				new ResponseEntity<>(rm, HttpStatus.OK);
			}else {
				logger.info("Unsuccess transfer webhook");
				return mappedErrorWithResponseModal(collectionResponse.getBody(), collectionResponse.getStatusCode(),
						Arrays.asList(), "internal");
			}
			}
			return mappedErrorWithResponseModal(token.getBody().getErrorMessage(), token.getStatusCode(),
					Arrays.asList(), "internal");
		}catch (HttpClientErrorException httpClentErrorEx) {
			logger.error("Error HttpClientErrorException -> " + httpClentErrorEx.getResponseBodyAsString());
			saveApiCallInDatabase(webhookCollectionUrl, request, httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode().value());
			return mappedErrorWithResponseModal(httpClentErrorEx.getResponseBodyAsString(),
					httpClentErrorEx.getStatusCode(), Arrays.asList(), "");
		} catch (Exception e) {
			logger.error("Error -> " + e);
			return mappedErrorWithResponseModal(ResponseMessage.WALLEX_KYC_NOT_DONE.getValue(), HttpStatus.BAD_REQUEST,
					Arrays.asList(), "Exception");
		}
	}
	
	
	// mapping between signup api response to api Wallex User Signup Response
		public WallexDepositResponse convertToWallexDepositResponse(String responseData) {
			logger.info("InSide Class Name :- " + getClass() + ", Method :- "
					+ new Throwable().getStackTrace()[0].getMethodName());

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				WallexDepositResponse wallexDepositResponse = objectMapper.readValue(responseData,WallexDepositResponse.class);
				return wallexDepositResponse;
			} catch (Exception e) {
				logger.error("Error -> " + e);
				return new WallexDepositResponse();
			}
		}
}
