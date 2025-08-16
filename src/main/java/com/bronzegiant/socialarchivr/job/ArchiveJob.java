package com.bronzegiant.socialarchivr.job;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ArchiveJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long archiveId;
    private String username;
    private SocialMediaPlatform platform;
    
    private JobStatus status; // PENDING, IN_PROGRESS, COMPLETE, FAILED
    private String errorMessage;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public ArchiveJob() {}
    
    public ArchiveJob(Long archiveId, String username, SocialMediaPlatform platform) {
    	this.archiveId = archiveId;
    	this.username = username;
    	this.platform = platform;
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
	
	public SocialMediaPlatform getPlatform() {
		return platform;
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

