package com.bronzegiant.socialarchivr.socialaccount;

import java.time.Instant;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;

import jakarta.persistence.*;

@Entity
@Table(
		name = "social_accounts",    
		uniqueConstraints = @UniqueConstraint(columnNames = {"username", "platform"})
)
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "archive_id")
    private Long archiveId;

    @Enumerated(EnumType.STRING)
    private SocialMediaPlatform platform; // FACEBOOK, INSTAGRAM, etc.

    @Column
    private String username;

    @Column(name = "access_token", length = 2048)
    private String accessToken;

    @Column(name = "expires_in")
    private Long tokenExpiresInMs;

    private Instant createdAt = Instant.now();
    
    public SocialAccount() {}
    
    public SocialAccount(SocialMediaPlatform platform, Long archiveId, String username, String accessToken, Long expiresIn) {
    	this.platform = platform;
    	this.archiveId = archiveId;
    	this.username = username;
    	this.accessToken = accessToken;
    	this.tokenExpiresInMs = expiresIn;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(Long archiveId) {
		this.archiveId = archiveId;
	}

	public SocialMediaPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(SocialMediaPlatform platform) {
		this.platform = platform;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getTokenExpiresIn() {
		return tokenExpiresInMs;
	}

	public void setTokenExpiresInMs(Long ms) {
		this.tokenExpiresInMs = ms;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

    
}

