package com.bronzegiant.socialarchivr.security;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import com.bronzegiant.socialarchivr.user.User;
import com.bronzegiant.socialarchivr.user.UserRepository;

public class AccessTokenManager {
	
	public static AccessToken createToken(LoginCredentials credentials, UserRepository userRepo) {
		
		Optional<User> userOpt = userRepo.findByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
	    if(userOpt.isPresent()) {
	        // login success
	    	User user = userOpt.get();
			
			SecureRandom secureRandom = new SecureRandom();
			byte[] randomBytes = new byte[24];
			secureRandom.nextBytes(randomBytes);
			String tokenId = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
			
			return new AccessToken(tokenId, user.getFirstname() + ' ' + user.getLastname(),  LocalDateTime.now(), user.getId());
	    }else {
	    	return null;
	    }
	}
}
