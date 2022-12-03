package com.example.udoncar.model;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {
    private String userId ="";
    private String name ="";
    private String message="";
    private Date createAt=null;

    public Chat(String userId, String name, String message, Date createAt) {
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.createAt = createAt;
    }
    public Chat(){
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getcreateAt() {
        return createAt;
    }

    public void setcreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
