package com.example.udoncar.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String post_id;
    private String title;
    private String content;
    private String dest;
    private String position;
    private String user_id;
    private String opt_age;
    private String opt_sex;
    private Date createAt;
    private Date meetAt;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOpt_age() {
        return opt_age;
    }

    public void setOpt_age(String opt_age) {
        this.opt_age = opt_age;
    }

    public String getOpt_sex() {
        return opt_sex;
    }

    public void setOpt_sex(String opt_sex) {
        this.opt_sex = opt_sex;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getMeetAt() {
        return meetAt;
    }

    public void setMeetAt(Date meetAt) {
        this.meetAt = meetAt;
    }
}
