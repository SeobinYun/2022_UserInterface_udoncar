package com.example.udoncar.model;

import java.io.Serializable;

public class History implements Serializable {
    private String hist_id;
    private String post_id;
    private String user_id;

    public String getHist_id() {
        return hist_id;
    }

    public void setHist_id(String hist_id) {
        this.hist_id = hist_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
