package com.bankservices.dto;

import java.io.Serializable;

public class WallexWebhookDTO implements Serializable {

	private static final long serialVersionUID = -8411535281028976100L;
	private String resource;
	private String resourceId;
	private String status;
	private String reason;

	public WallexWebhookDTO() {
		super();
	}

	public WallexWebhookDTO(String resource, String resourceId, String status, String reason) {
		super();
		this.resource = resource;
		this.resourceId = resourceId;
		this.status = status;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "WallexWebhookDTO [resource=" + resource + ", resourceId=" + resourceId + ", status=" + status
				+ ", reason=" + reason + "]";
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}