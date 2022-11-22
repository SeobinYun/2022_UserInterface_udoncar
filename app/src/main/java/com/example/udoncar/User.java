package com.example.udoncar;

import java.util.List;

public class User {
    private String id;
    private String pw;
    private String name;
    private List<String> region;
    private String sex;
    private String age;


    public User(){}

    public User(String id, String pw, String name, List<String> region, String sex, String age){

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
