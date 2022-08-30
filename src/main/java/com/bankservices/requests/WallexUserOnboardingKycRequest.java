package com.bankservices.requests;

public class WallexUserOnboardingKycRequest {
	
	private String wallexUserId;

	public WallexUserOnboardingKycRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WallexUserOnboardingKycRequest(String wallexUserId) {
		super();
		this.wallexUserId = wallexUserId;
	}

	public String getWallexUserId() {
		return wallexUserId;
	}

	public void setWallexUserId(String wallexUserId) {
		this.wallexUserId = wallexUserId;
	}

	@Override
	public String toString() {
		return "WallexUserOnboardingKycRequest [wallexUserId=" + wallexUserId + "]";
	}

}
