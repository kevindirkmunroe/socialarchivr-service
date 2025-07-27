package com.bronzegiant.socialarchivr.user;

import java.util.ArrayList;
import java.util.List;

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
import com.bronzegiant.socialarchivr.archive.ArchiveRepository;

@Configuration
public class DatabaseLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
	
	@Bean
	CommandLineRunner initDatabase(
		UserRepository repository, 
		SocialAccountRepository saRepository, 
		ArchiveRepository archiveRepository,
		ArchiveLogRepository archiveLogRepository,
		PostRepository postRepository) {
		
		return args -> {

			User mainTestUser = new User("Kevin", "Munroe", "kevin.munroe@gmail.com", "asdf");
			// Users
			try {
			    mainTestUser = repository.save(mainTestUser);
			    log.info("Preloading " + mainTestUser);
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
			
			// Archives
			Archive mainTestArchive = new Archive(mainTestUser.getId(), "morpheus");
		    List<Archive> archives = new ArrayList<Archive>();
		    archives.add(mainTestArchive);
		    archives.add(new Archive(mainTestUser.getId(), "trinity"));
			try {
			    List<Archive> savedArchives = archiveRepository.saveAll(archives);
			    log.info("Preloading " + archives);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not to archives: " + e.getMessage());
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
			
		    List<Post> posts = new ArrayList<Post>();
		    posts.add(new Post(mainTestUser.getId(), "This is post 1", mainTestArchive.getId()));
		    posts.add(new Post(mainTestUser.getId(), "This is post 2. Not very exciting.", mainTestArchive.getId()));
			try {
			    List<Post> savedPosts = postRepository.saveAll(posts);
			    log.info("Preloading " + savedPosts);
			} catch (DataIntegrityViolationException e) {
			    log.error("Could not save to posts: " + e.getMessage());
			}
		
		};
	}
}
