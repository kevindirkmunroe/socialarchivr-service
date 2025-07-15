package com.bronzegiant.socialarchivr.security;

public class LoginCredentials {

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	private final String email;
	private final String password;
	
	public LoginCredentials(String email, String pwd) {
		this.email = email;
		this.password = pwd;
	}
	
}
