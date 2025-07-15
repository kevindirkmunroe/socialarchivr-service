package com.bronzegiant.socialarchivr.security;

class InvalidCredentialsException extends RuntimeException {

	  private static final long serialVersionUID = 1L;

	  InvalidCredentialsException(Long id) {
	    super("Could not find user " + id);
	  }
}
