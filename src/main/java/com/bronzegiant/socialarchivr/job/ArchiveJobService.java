package com.bronzegiant.socialarchivr.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ArchiveJobService {

    @Autowired private ArchiveJobRepository jobRepo;

    @Async
    public void runArchiveJob(ArchiveJob job) {
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepo.save(job);

        try {
            // 1. Call Facebook API
            // 2. Store metadata in Postgres
            // 3. Store documents in MongoDB
            // 4. Upload media to S3
        	
        	// For now, simulate a 1 second process.
        	Thread.sleep(1000); 

            job.setStatus(JobStatus.COMPLETE);
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
        }

        jobRepo.save(job);
    }
}

