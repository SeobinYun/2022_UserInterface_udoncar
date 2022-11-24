package com.example.udoncar;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//
//    public MainActivity(Intent intent) {
//
//    }


    // 사용자 정보 액세스
    private void getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            Log.d(TAG,"getCurrentUser 성공");
        }
    }

    @Override
    public void onStart() {
        // 활동 초기화 시 사용자가 로그인 되어있는지 확인
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            reload();
//        }
    }

    private void reload() {
    }
}