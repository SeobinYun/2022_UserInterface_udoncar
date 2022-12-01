package com.example.udoncar.model;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.udoncar.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String id;
    private String pw;
    private String name;
    private List<String> region;
    private String sex;
    private String age;

    public User(){}

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
