package com.bronzegiant.socialarchivr.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class FacebookPost {

    private String id;
    private String message;

    @JsonProperty("created_time")
    private ZonedDateTime createdTime;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
