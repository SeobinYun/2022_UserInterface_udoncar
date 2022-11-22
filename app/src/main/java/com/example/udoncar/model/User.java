package com.example.udoncar.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String id;
    private String pw;
    private String name;
    private List<String> region;
    private String sex;
    private String age;


    public User(){}

    public User(String id, String pw, String name, List<String> region, String sex, String age){

    }
    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId(){
        return id;
    }

    public String getPw(){
        return pw;
    }

    public String getName(){
        return name;
    }

    public List<String> getRegions(){
        return region;
    }

    public String getSex(){
        return sex;
    }

    public String getAge(){
        return age;
    }
}
