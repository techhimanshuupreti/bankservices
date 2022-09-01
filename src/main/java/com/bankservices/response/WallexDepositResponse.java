package com.bankservices.response;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WallexDepositResponse {

	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal amount;

	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal amountOrigin;

	@JsonInclude(value = Include.NON_NULL)
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

	@JsonInclude(value = Include.NON_NULL)
	private Object sender;

	@JsonInclude(value = Include.NON_NULL)
	private Object paymentDetails;

	public WallexDepositResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WallexDepositResponse(BigDecimal amount, BigDecimal amountOrigin, BigDecimal fee, String status, String id,
			String accountId, String collectionAccountId, String currency, String creditedAt, String paymentType,
			JSONObject sender, JSONObject paymentDetails) {
		super();
		this.amount = amount;
		this.amountOrigin = amountOrigin;
		this.fee = fee;
		this.status = status;
		this.id = id;
		this.accountId = accountId;
		this.collectionAccountId = collectionAccountId;
		this.currency = currency;
		this.creditedAt = creditedAt;
		this.paymentType = paymentType;
		this.sender = sender;
		this.paymentDetails = paymentDetails;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountOrigin() {
		return amountOrigin;
	}

	public void setAmountOrigin(BigDecimal amountOrigin) {
		this.amountOrigin = amountOrigin;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCollectionAccountId() {
		return collectionAccountId;
	}

	public void setCollectionAccountId(String collectionAccountId) {
		this.collectionAccountId = collectionAccountId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCreditedAt() {
		return creditedAt;
	}

	public void setCreditedAt(String creditedAt) {
		this.creditedAt = creditedAt;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public JSONObject getSender() {
		return sender;
	}

	public void setSender(JSONObject sender) {
		this.sender = sender;
	}

	public JSONObject getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(JSONObject paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	@Override
	public String toString() {
		return "WallexDepositResponse [amount=" + amount + ", amountOrigin=" + amountOrigin + ", fee=" + fee
				+ ", status=" + status + ", id=" + id + ", accountId=" + accountId + ", collectionAccountId="
				+ collectionAccountId + ", currency=" + currency + ", creditedAt=" + creditedAt + ", paymentType="
				+ paymentType + ", sender=" + sender + ", paymentDetails=" + paymentDetails + "]";
	}
	
}
