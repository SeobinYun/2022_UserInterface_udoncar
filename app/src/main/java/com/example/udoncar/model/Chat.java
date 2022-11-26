package com.example.udoncar.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Chat implements Serializable {
    private String msgId;
    private String userId;
    private String name;
    private String message;
    private Timestamp sendtime;

    public Chat(String msgId, String userId, String name, String message, Timestamp sendtime) {
        this.msgId = msgId;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.sendtime = sendtime;
    }
    public Chat(){
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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

    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }
}
