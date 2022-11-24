package com.example.udoncar.model;

import java.util.List;

public class ChatUserList {
    private String histId;
    private List<String> userId;

    public ChatUserList(String histId, List<String> userId) {
        this.histId = histId;
        this.userId = userId;
    }

    public String getHistId() {
        return histId;
    }

    public void setHistId(String histId) {
        this.histId = histId;
    }

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }
}
