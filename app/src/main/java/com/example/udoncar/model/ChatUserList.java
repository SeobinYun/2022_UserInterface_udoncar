package com.example.udoncar.model;

import java.util.List;

public class ChatUserList {
    private String histId;
    private List<String> usersId;

    public ChatUserList(String histId, List<String> userId) {
        this.histId = histId;
        this.usersId = userId;
    }

    public ChatUserList(){
    }

    public String getHistId() {
        return histId;
    }

    public void setHistId(String histId) {
        this.histId = histId;
    }

    public List<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<String> userId) {
        this.usersId = userId;
    }
}
