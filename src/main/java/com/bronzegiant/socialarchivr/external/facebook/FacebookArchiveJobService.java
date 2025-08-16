package com.bronzegiant.socialarchivr.external.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bronzegiant.socialarchivr.job.AbstractArchiveJobService;
import com.bronzegiant.socialarchivr.job.ArchiveJob;
import com.bronzegiant.socialarchivr.job.ArchiveJobRepository;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccount;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccountRepository;

@Service
public class FacebookArchiveJobService extends AbstractArchiveJobService {
	
	@Autowired
	protected FacebookClient facebookClient;
	protected SocialAccountRepository socialAccountRepository;

    public FacebookArchiveJobService(ArchiveJobRepository jobRepo, 
    		SocialAccountRepository socialAccountRepo,
    		FacebookClient facebookClient) {
        super(jobRepo);
        this.socialAccountRepository = socialAccountRepo;
    }
    
	@Override
	protected void preflight(ArchiveJob job) throws Exception {
		SocialAccount acct = 
				socialAccountRepository.findByArchiveIdAndUsernameAndPlatform(job.getArchiveId(), job.getUsername(), job.getPlatform());
		
		if(acct == null) {
			throw new Exception("Social Account for " + job.getUsername() + " not found!");
		}
		
		if(!facebookClient.isFacebookTokenValid(acct.getAccessToken())){
			throw new Exception("Token for " + job.getUsername() + " is invalid/expired, please login to " + job.getPlatform() + " again.");
		}
	}

	@Override
	protected void execute() throws Exception {


        // 1. Call Facebook API
        // 2. Store metadata in Postgres
        // 3. Store documents in MongoDB
        // 4. Upload media to S3
		
		// For now, simulate a 1 second process.
    	Thread.sleep(1000); 
	}

	@Override
	protected void cleanup() {
		// TODO Auto-generated method stub

	}

}
