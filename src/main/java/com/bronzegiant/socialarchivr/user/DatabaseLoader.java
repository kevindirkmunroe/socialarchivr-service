package com.bronzegiant.socialarchivr.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

@Configuration
public class DatabaseLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
	
	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		
		return args -> {
			try {
			    User savedUser = repository.save(
			        new User("Kevin", "Munroe", "kevin.munroe@gmail.com")
			    );
			    log.info("Preloading " + savedUser);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save user because the email already exists: " + e.getMessage());
			}			
			
			try {
			    User savedUser = repository.save(
			        new User("Frodo", "Baggins", "frodo@lotr.org")
			    );
			    log.info("Preloading " + savedUser);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save user because the email already exists: " + e.getMessage());
			}		};
	}
}
