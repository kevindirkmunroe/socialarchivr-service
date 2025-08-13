package com.bronzegiant.socialarchivr.log;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;
import com.bronzegiant.socialarchivr.archive.Archive;


@Entity
@Table(name = "archive_log")
public class ArchiveLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "archive_id", nullable = false)
    private Archive archive;

    @Column(name = "archive_date_start", nullable = false)
    private LocalDateTime archiveDateStart;

    @Column(name = "archive_date_completed")
    private LocalDateTime archiveDateCompleted;

    @Column(name = "archive_trigger_type", nullable = false)
    private String archiveTriggerType;
    
    @Column(name = "social_media_platform", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialMediaPlatform socialMediaPlatform;
    
    @Column(name = "social_media_username", nullable = false)
    private String socialMediaUsername;


	// Constructors
    public ArchiveLog() {
        this.archiveDateStart = LocalDateTime.now();
    }

    public ArchiveLog(Archive archive, String archiveTriggerType, SocialMediaPlatform socialMediaPlatform, String socialMediaUsername) {
        this.archive = archive;
        this.archiveTriggerType = archiveTriggerType;
        this.archiveDateStart = LocalDateTime.now();
        this.archiveDateCompleted = LocalDateTime.now();
        this.socialMediaPlatform = socialMediaPlatform;
        this.socialMediaUsername = socialMediaUsername;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public LocalDateTime getArchiveDateStart() {
        return archiveDateStart;
    }

    public void setArchiveDateStart(LocalDateTime archiveDateStart) {
        this.archiveDateStart = archiveDateStart;
    }

    public LocalDateTime getArchiveDateCompleted() {
        return archiveDateCompleted;
    }

    public void setArchiveDateCompleted(LocalDateTime archiveDateCompleted) {
        this.archiveDateCompleted = archiveDateCompleted;
    }

    public String getArchiveTriggerType() {
        return archiveTriggerType;
    }

    public void setArchiveTrigger(String archiveTriggerType) {
        this.archiveTriggerType = archiveTriggerType;
    }
    
    public SocialMediaPlatform getSocialMediaPlatform() {
		return socialMediaPlatform;
	}

	public void setSocialMediaPlatform(SocialMediaPlatform socialMediaPlatform) {
		this.socialMediaPlatform = socialMediaPlatform;
	}
	
    public String getSocialMediaUsername() {
		return socialMediaUsername;
	}

	public void setSocialMediaUsername(String socialMediaUsername) {
		this.socialMediaUsername = socialMediaUsername;
	}
}

