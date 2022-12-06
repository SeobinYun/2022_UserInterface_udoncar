package com.example.udoncar.model;

import java.io.Serializable;
import java.util.List;

public class History implements Serializable {
    private String histId;
    private String postId;
    private List<String> usersId;

    public History(String histId, String postId, List<String> usersId) {
        this.histId = histId;
        this.postId = postId;
        this.usersId = usersId;
    }
    public History(){
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

    public List<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<String> usersId) {
        this.usersId = usersId;
    }
}
