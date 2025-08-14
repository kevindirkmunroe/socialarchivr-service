package com.bronzegiant.socialarchivr.job;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;

public class ArchiveJobRequest {
    private String username;
    private Long archiveId;
    SocialMediaPlatform platform;

    // Constructors
    public ArchiveJobRequest() {}

    public ArchiveJobRequest(String username, Long archiveId, SocialMediaPlatform platform) {
        this.username = username;
        this.archiveId = archiveId;
        this.platform = platform;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Long archiveId) {
        this.archiveId = archiveId;
    }
    
    public SocialMediaPlatform getPlatform() {
    	return this.platform;
    }
    
    public void setPlatform(SocialMediaPlatform p) {
    	this.platform = p;
    }
}

