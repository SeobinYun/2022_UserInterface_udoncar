package com.example.udoncar.model;

import java.io.Serializable;

public class History implements Serializable {
    private String histId;
    private String postId;
    private String userId;

    public History(String histId, String postId, String userId) {
        this.histId = histId;
        this.postId = postId;
        this.userId = userId;
    }

    public String gethistId() {
        return histId;
    }

    public void sethistId(String histId) {
        this.histId = histId;
    }

    public String getpostId() {
        return postId;
    }

    public void setpostId(String postId) {
        this.postId = postId;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }
}
