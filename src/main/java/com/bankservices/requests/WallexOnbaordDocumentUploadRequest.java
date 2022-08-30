package com.bankservices.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WallexOnbaordDocumentUploadRequest {

	private String wallexDocumentId;
	private String wallexDocumentUploadUrl;
	private String wallexDocumentDownloadUrl;
	private String documentReference;
	private String documentType;
	private String documentName;
	private MultipartFile onboardingDocumentList;

	public WallexOnbaordDocumentUploadRequest() {
		super();
	}

	public WallexOnbaordDocumentUploadRequest(String wallexDocumentId, String wallexDocumentUploadUrl,
			String wallexDocumentDownloadUrl, String documentReference, String documentType, String documentName,
			MultipartFile onboardingDocumentList) {
		super();
		this.wallexDocumentId = wallexDocumentId;
		this.wallexDocumentUploadUrl = wallexDocumentUploadUrl;
		this.wallexDocumentDownloadUrl = wallexDocumentDownloadUrl;
		this.documentReference = documentReference;
		this.documentType = documentType;
		this.documentName = documentName;
		this.onboardingDocumentList = onboardingDocumentList;
	}

	public String getWallexDocumentDownloadUrl() {
		return wallexDocumentDownloadUrl;
	}

	public void setWallexDocumentDownloadUrl(String wallexDocumentDownloadUrl) {
		this.wallexDocumentDownloadUrl = wallexDocumentDownloadUrl;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public WallexOnbaordDocumentUploadRequest(String documentName) {
		super();
		this.documentName = documentName;
	}

	public String getWallexDocumentId() {
		return wallexDocumentId;
	}

	public void setWallexDocumentId(String wallexDocumentId) {
		this.wallexDocumentId = wallexDocumentId;
	}

	public String getWallexDocumentUploadUrl() {
		return wallexDocumentUploadUrl;
	}

	public void setWallexDocumentUploadUrl(String wallexDocumentUploadUrl) {
		this.wallexDocumentUploadUrl = wallexDocumentUploadUrl;
	}

	public String getDocumentReference() {
		return documentReference;
	}

	public void setDocumentReference(String documentReference) {
		this.documentReference = documentReference;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public MultipartFile getOnboardingDocumentList() {
		return onboardingDocumentList;
	}

	public void setOnboardingDocumentList(MultipartFile onboardingDocumentList) {
		this.onboardingDocumentList = onboardingDocumentList;
	}

	@Override
	public String toString() {
		return "WallexOnbaordDocumentUploadRequest [wallexDocumentId=" + wallexDocumentId + ", wallexDocumentUploadUrl="
				+ wallexDocumentUploadUrl + ", wallexDocumentDownloadUrl=" + wallexDocumentDownloadUrl
				+ ", documentReference=" + documentReference + ", documentType=" + documentType + ", documentName="
				+ documentName + ", onboardingDocumentList=" + onboardingDocumentList + "]";
	}

}
