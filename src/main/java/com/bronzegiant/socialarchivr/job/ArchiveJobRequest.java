package com.bronzegiant.socialarchivr.job;

public class ArchiveJobRequest {
    private String username;
    private Long archiveId;

    // Constructors
    public ArchiveJobRequest() {}

    public ArchiveJobRequest(String username, Long archiveId) {
        this.username = username;
        this.archiveId = archiveId;
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
}

