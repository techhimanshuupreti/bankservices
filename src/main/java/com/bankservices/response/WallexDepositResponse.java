package com.bankservices.response;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class WallexDepositResponse {
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private BigDecimal amount;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private BigDecimal amountOrigin;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private BigDecimal fee;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String status;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String id;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String accountId;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String collectionAccountId;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String currency;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String creditedAt;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private String paymentType;
	
	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private JSONObject  sender;

	@JsonInclude(value = Include.NON_NULL, content = Include.NON_EMPTY)
	private JSONObject  paymentDetails;
	
	

}
