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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id;
    private String pw;
    private String name;
    private List<String> region;
    private String sex;
    private String age;


    public User(){}

    public User(String id, String pw, String name, List<String> region, String sex, String age){
        Map<String, Object> docData = new HashMap<>();
        if(id!=null){
        docData.put("id", id);}
        if(pw!=null){
        docData.put("pw", pw);}
        if(name!=null){
        docData.put("name", name);}
        if(region!=null){
        docData.put("region", region);}
        if(sex!=null){
        docData.put("sex", sex);}
        if(age!=null){
        docData.put("age", age);}

        db.collection("users").document(id)
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
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
