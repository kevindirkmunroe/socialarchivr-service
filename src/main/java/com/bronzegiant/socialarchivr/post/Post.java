package com.bronzegiant.socialarchivr.post;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.bronzegiant.socialarchivr.archive.Archive;
import com.bronzegiant.socialarchivr.socialaccount.SocialAccount;
import com.bronzegiant.socialarchivr.user.User;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "archive_id", nullable = false)
    private Archive archive;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccount socialAccount;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
 
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(nullable = true)
    private String mongoId;


	// Constructors
    public Post() {}

    public Post(User user, Archive archive, SocialAccount socialAccount, String title) {
        this.user = user;
        this.archive = archive;
        this.socialAccount = socialAccount;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getMongoId() {
		return mongoId;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Column(nullable = true)
    private String thumbnailUrl;


    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    
    public Archive getArchive() { return archive; }
    
    public SocialAccount getSocialAccount() {
    	return socialAccount;
    }

    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }

    public LocalDateTime getCreatedDate() { return createdAt; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdAt = createdDate; }
    
    
    public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}

