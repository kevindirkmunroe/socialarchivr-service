package com.bronzegiant.socialarchivr.user.profileimage;

import jakarta.persistence.*;
import java.time.Instant;

import com.bronzegiant.socialarchivr.user.User;

@Entity
@Table(name = "user_profile_image")
public class UserProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many images per user? Usually one profile image, but you can adjust
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private Instant uploadedAt = Instant.now();

    // Constructors
    public UserProfileImage() {}

    public UserProfileImage(User user, byte[] imageData, String fileName, String contentType) {
        this.user = user;
        this.imageData = imageData;
        this.fileName = fileName;
        this.contentType = contentType;
        this.uploadedAt = Instant.now();
    }

    // Getters and setters below...

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}

