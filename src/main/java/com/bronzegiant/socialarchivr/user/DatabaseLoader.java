package com.bronzegiant.socialarchivr.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import com.bronzegiant.socialarchivr.socialaccount.SocialAccount;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccountRepository;
import com.bronzegiant.socialarchivr.socialaccount.SocialMediaPlatform;

@Configuration
public class DatabaseLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
	
	@Bean
	CommandLineRunner initDatabase(UserRepository repository, SocialAccountRepository saRepository) {
		
		return args -> {
			try {
			    User savedUser = repository.save(
			        new User("Kevin", "Munroe", "kevin.munroe@gmail.com", "$tr0ngPassw0rd1")
			    );
			    log.info("Preloading " + savedUser);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save user: " + e.getMessage());
			}			
			
			try {
			    User savedUser = repository.save(
			        new User("Frodo", "Baggins", "frodo@lotr.org", "$tr0ngPassw0rd2")
			    );
			    log.info("Preloading " + savedUser);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save user: " + e.getMessage());
			}		
			
		    List<SocialAccount> entitiesToSave = new ArrayList<SocialAccount>();
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.FACEBOOK, 5000L,  "kevindirk", "accessToken1", 10000L));
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.FACEBOOK, 5000L,  "kevinthecomedian", "accessToken2", 10000L));
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.INSTAGRAM, 5000L,  "itskevinmunroe", "accessToken3", 10000L));
		    
			try {
			    List<SocialAccount> savedSocialAccounts = saRepository.saveAll(entitiesToSave);
			    log.info("Preloading " + savedSocialAccounts);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save social accounts: " + e.getMessage());
			}	
			
		};
	}
}
