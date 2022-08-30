package com.bankservices.requests;

import java.util.List;

public class WallexUserOnboardingDocumentsRequest {

	private String merchantId;
	private String wallexUserId;
	private List<WallexOnbaordDocumentUploadRequest> onboardDocs;

	public WallexUserOnboardingDocumentsRequest() {
		super();
	}

	public WallexUserOnboardingDocumentsRequest(String merchantId, String wallexUserId,
			List<WallexOnbaordDocumentUploadRequest> onboardDocs) {
		super();
		this.merchantId = merchantId;
		this.wallexUserId = wallexUserId;
		this.onboardDocs = onboardDocs;
	}

	public List<WallexOnbaordDocumentUploadRequest> getOnboardDocs() {
		return onboardDocs;
	}

	public void setOnboardDocs(List<WallexOnbaordDocumentUploadRequest> onboardDocs) {
		this.onboardDocs = onboardDocs;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getWallexUserId() {
		return wallexUserId;
	}

	public void setWallexUserId(String wallexUserId) {
		this.wallexUserId = wallexUserId;
	}

	@Override
	public String toString() {
		return "WallexUserOnboardingDocumentsRequest [merchantId=" + merchantId + ", wallexUserId=" + wallexUserId
				+ ", onboardDocs=" + onboardDocs + "]";
	}

}
