package com.bronzegiant.socialarchivr.job;

import org.springframework.scheduling.annotation.Async;

public abstract class AbstractArchiveJobService {

    private ArchiveJobRepository jobRepo;
    
    protected abstract void preflight(ArchiveJob job) throws Exception;
    protected abstract void execute() throws Exception;
    protected abstract void cleanup();
    
    protected AbstractArchiveJobService(ArchiveJobRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Async
    public void runArchiveJob(ArchiveJob job) {
    	
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepo.save(job);

        try {
        	preflight(job);
        	
        	execute();

            job.setStatus(JobStatus.COMPLETE);
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
        }

        jobRepo.save(job);
        cleanup();
    }
}

