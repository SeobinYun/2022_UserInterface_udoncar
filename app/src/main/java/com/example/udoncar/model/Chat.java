package com.example.udoncar.model;

import java.io.Serializable;

public class Chat implements Serializable {
    private String msgId;
    private String userId;
    private String name;

    public Chat(String msgId, String userId, String name, String message, String sendtime) {
        this.msgId = msgId;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.sendtime = sendtime;
    }

    private String message;
    private String sendtime;

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

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
