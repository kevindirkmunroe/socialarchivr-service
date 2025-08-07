package com.bronzegiant.socialarchivr.job;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ArchiveJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Long archiveId;
    private JobStatus status; // PENDING, IN_PROGRESS, COMPLETE, FAILED
    private String errorMessage;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public ArchiveJob() {}
    
    public ArchiveJob(String username, Long archiveId) {
    	this.username = username;
    	this.archiveId = archiveId;
    	this.setStatus(JobStatus.PENDING);
    }
    
    public Long getId() {
    	return this.id;
    }


	public String getUsername() {
		return username;
	}
	
	public Long getArchiveId() {
		return archiveId;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}
}

