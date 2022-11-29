package com.example.udoncar.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {
    private String postId;
    private String title;
    private String content;
    private String dest;
    private String position;
    private String userId;
    private String optAge;
    private String optSex;
    private Date createAt;
    private Date meetAt;
//    private List<String> destspn;
    //, List<String> destspn

    public Post(String postId, String title, String content, String dest, String position,
                String userId, String optAge, String optSex, Date createAt, Date meetAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.dest = dest;
        this.position = position;
        this.userId = userId;
        this.optAge = optAge;
        this.optSex = optSex;
        this.createAt = createAt;
        this.meetAt = meetAt;
 //       this.destspn = destspn;
    }

    public String getpostId() {
        return postId;
    }

    public void setpostId(String postId) {
        this.postId = postId;
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

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getoptAge() {
        return optAge;
    }

    public void setoptAge(String optAge) {
        this.optAge = optAge;
    }

    public String getoptSex() {
        return optSex;
    }

    public void setoptSex(String optSex) {
        this.optSex = optSex;
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

 //   public List<String> getDestspn(){
 //       return destspn;
//    }

}
