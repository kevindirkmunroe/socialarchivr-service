package com.bronzegiant.socialarchivr.user;

class UserNotFoundException extends RuntimeException {

	  UserNotFoundException(Long id) {
	    super("Could not find user " + id);
	  }
}
