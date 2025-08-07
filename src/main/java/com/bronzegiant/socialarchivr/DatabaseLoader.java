package com.bronzegiant.socialarchivr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import com.bronzegiant.socialarchivr.archive.Archive;
import com.bronzegiant.socialarchivr.log.ArchiveLog;
import com.bronzegiant.socialarchivr.log.ArchiveLogRepository;
import com.bronzegiant.socialarchivr.post.Post;
import com.bronzegiant.socialarchivr.post.PostRepository;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccount;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccountRepository;
import com.bronzegiant.socialarchivr.socialaccount.SocialMediaPlatform;
import com.bronzegiant.socialarchivr.user.User;
import com.bronzegiant.socialarchivr.user.UserRepository;
import com.bronzegiant.socialarchivr.archive.ArchiveRepository;
import com.bronzegiant.socialarchivr.job.ArchiveJob;
import com.bronzegiant.socialarchivr.job.ArchiveJobRepository;

@Configuration
public class DatabaseLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
	
	@Bean
	CommandLineRunner initDatabase(
		UserRepository repository, 
		SocialAccountRepository saRepository, 
		ArchiveRepository archiveRepository,
		ArchiveLogRepository archiveLogRepository,
		ArchiveJobRepository archiveJobRepository,
		PostRepository postRepository) {
		
		return args -> {

			User mainTestUser = null;
			Optional<User> test = repository.findByEmailAndPassword("kevin.munroe@gmail.com", "asdf");
			if(test.isPresent()) {
				mainTestUser = test.get();
			}else {
				User newUser = new User("Kevin", "Munroe", "kevin.munroe@gmail.com", "asdf");
				// Users
				try {
				    mainTestUser = repository.saveAndFlush(newUser);
				    log.info("Preloading " + mainTestUser);
				} catch (DataIntegrityViolationException e) {
				    log.error("Could not save user: " + e.getMessage());
				}			
			}	
			
			if(mainTestUser == null) {
				throw new Exception("Main Test User could not be initiazed.");
			}
			
			Optional<User> test2 = repository.findByEmailAndPassword("frodo@lotr.org", "$tr0ngPassw0rd2");
			if(!test2.isPresent()) {
				try {
				    User savedUser = repository.saveAndFlush(
				        new User("Frodo", "Baggins", "frodo@lotr.org", "$tr0ngPassw0rd2")
				    );
				    log.info("Preloading " + savedUser);
				} catch (DataIntegrityViolationException e) {
				    log.error("Could not save user: " + e.getMessage());
				}		
			}
			
			// Archives
			Archive mainTestArchive = archiveRepository.findByName("morpehus");
			if(mainTestArchive == null) {
				try {
					mainTestArchive = archiveRepository.saveAndFlush(new Archive(mainTestUser.getId(), "morpheus"));
					log.info("Saved main archive id " + mainTestArchive.getId());
				} catch (DataIntegrityViolationException e) {
				    log.error("Could not save archive: " + e.getMessage());
				}				
			}

			Archive trinity = archiveRepository.findByName("trinity");
			if(trinity == null) {
				try {
					archiveRepository.saveAndFlush(new Archive(mainTestUser.getId(), "trinity"));
					log.info("Saved trinity" + mainTestArchive.getId());
				} catch (DataIntegrityViolationException e) {
				    log.error("Could not save archive: " + e.getMessage());
				}				
			}

			// social_accounts
		    List<SocialAccount> entitiesToSave = new ArrayList<SocialAccount>();
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.FACEBOOK, mainTestArchive.getId(),  "kevindirk", "accessToken1", 10000L));
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.FACEBOOK, mainTestArchive.getId(),  "kevinthecomedian", "accessToken2", 10000L));
		    entitiesToSave.add(new SocialAccount(SocialMediaPlatform.INSTAGRAM, mainTestArchive.getId(),  "itskevinmunroe", "accessToken3", 10000L));
		    
			try {
			    List<SocialAccount> savedSocialAccounts = saRepository.saveAll(entitiesToSave);
			    log.info("Preloading " + savedSocialAccounts);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save to social_accounts" + e.getMessage());
			}	
			
			// archive_log
		    List<ArchiveLog> logs = new ArrayList<ArchiveLog>();
		    logs.add(new ArchiveLog(mainTestArchive, "AUTO", SocialMediaPlatform.FACEBOOK, "kevindirk"));
		    logs.add(new ArchiveLog(mainTestArchive, "MANUAL", SocialMediaPlatform.FACEBOOK, "kevindirk"));
		    logs.add(new ArchiveLog(mainTestArchive, "AUTO", SocialMediaPlatform.INSTAGRAM, "itskevinmunroe"));
		    logs.add(new ArchiveLog(mainTestArchive, "MANUAL", SocialMediaPlatform.INSTAGRAM, "itskevinmunroe"));
		    logs.add(new ArchiveLog(mainTestArchive, "AUTO", SocialMediaPlatform.FACEBOOK, "kevinthecomedian"));
		    logs.add(new ArchiveLog(mainTestArchive, "MANUAL", SocialMediaPlatform.FACEBOOK, "kevinthecomedian"));

		    
			try {
			    List<ArchiveLog> savedLogs = archiveLogRepository.saveAll(logs);
			    log.info("Preloading " + savedLogs);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save to archive_log: " + e.getMessage());
			}	
			
		    
		    // archive job
		    List<ArchiveJob> jobs = new ArrayList<ArchiveJob>();
		    jobs.add(new ArchiveJob("kevindirk", mainTestArchive.getId() ));
			try {
			    List<ArchiveJob> savedJobs = archiveJobRepository.saveAll(jobs);
			    log.info("Preloading " + jobs);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save to posts: " + e.getMessage());
			}
			
		    List<Post> posts = new ArrayList<Post>();
		    posts.add(new Post(mainTestUser.getId(), "This is post 1", mainTestArchive.getId()));
		    posts.add(new Post(mainTestUser.getId(), "This is post 2. Not very exciting.", mainTestArchive.getId()));
		    posts.add(new Post(mainTestUser.getId(), "This is post 3. What a barn burner this one is.", mainTestArchive.getId()));
			try {
			    List<Post> savedPosts = postRepository.saveAll(posts);
			    log.info("Preloading " + savedPosts);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save to posts: " + e.getMessage());
			}
		
		};
	}
}
