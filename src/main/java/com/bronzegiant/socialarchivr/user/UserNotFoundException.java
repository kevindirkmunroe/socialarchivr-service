package com.bronzegiant.socialarchivr.user;

public class UserNotFoundException extends RuntimeException {

	  UserNotFoundException(Long id) {
	    super("Could not find user " + id);
	  }
}
