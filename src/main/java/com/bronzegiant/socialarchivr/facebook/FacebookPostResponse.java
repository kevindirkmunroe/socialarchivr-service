package com.bronzegiant.socialarchivr.facebook;

import java.util.List;

public class FacebookPostResponse {

    private List<FacebookPost> data;

    // Getters and setters
    public List<FacebookPost> getData() {
        return data;
    }

    public void setData(List<FacebookPost> data) {
        this.data = data;
    }
}
