package com.bankservices.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "wallex_api")
public class WallexAPI implements Serializable {

	private static final long serialVersionUID = 9007777879661913156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, insertable = false, updatable = false)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String apiRequest;
	
	@Column(columnDefinition = "TEXT")
	private String apiResponse;
	private String apiEndpoint;
	private int statusCode;
	
	@Column(nullable = false, updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private Date createdAt;

	public int getStatusCode() {
		return statusCode;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public WallexAPI(long id, String apiRequest, String apiResponse, String apiEndpoint, int statusCode,
			Date createdAt) {
		super();
		this.id = id;
		this.apiRequest = apiRequest;
		this.apiResponse = apiResponse;
		this.apiEndpoint = apiEndpoint;
		this.statusCode = statusCode;
		this.createdAt = createdAt;
	}

	public WallexAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApiRequest() {
		return apiRequest;
	}

	public void setApiRequest(String apiRequest) {
		this.apiRequest = apiRequest;
	}

	public String getApiResponse() {
		return apiResponse;
	}

	public void setApiRespons(String apiResponse) {
		this.apiResponse = apiResponse;
	}

	@Override
	public String toString() {
		return "WallexAPI [id=" + id + ", apiRequest=" + apiRequest + ", apiResponse=" + apiResponse + ", statusCode="
				+ statusCode + ", createdAt=" + createdAt + "]";
	}

}
