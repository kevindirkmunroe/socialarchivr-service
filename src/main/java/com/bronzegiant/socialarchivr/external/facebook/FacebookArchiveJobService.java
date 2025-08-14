package com.bronzegiant.socialarchivr.external.facebook;

import org.springframework.stereotype.Service;

import com.bronzegiant.socialarchivr.job.AbstractArchiveJobService;
import com.bronzegiant.socialarchivr.job.ArchiveJobRepository;

@Service
public class FacebookArchiveJobService extends AbstractArchiveJobService {

    public FacebookArchiveJobService(ArchiveJobRepository jobRepo) {
        super(jobRepo);
    }
    
	@Override
	protected void preflight() throws Exception {
		// TODO Auto-generated method stub

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
