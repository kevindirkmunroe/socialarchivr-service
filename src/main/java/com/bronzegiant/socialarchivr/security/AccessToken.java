package com.bronzegiant.socialarchivr.security;

import java.time.LocalDateTime;

public class AccessToken {

	private final String tokenId;
	private final String userFullName;
	private final LocalDateTime expiration;
	private final Long userId;
	
	public AccessToken(String t, String n, LocalDateTime exp, Long userId) {
		this.tokenId = t;
		this.expiration = exp;
		this.userFullName = n;
		this.userId = userId;
	}
	
	public String getTokenId() {
		return tokenId;
	}
	
	public LocalDateTime getExpiration() {
		return expiration;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public Long getUserId() {
		return userId;
	}
}
