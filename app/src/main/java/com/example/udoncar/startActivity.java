package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 회원가입 클릭 시 액티비티 전환
        Button signup_btn = (Button)findViewById(R.id.signup);
        signup_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), signUpActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 클릭 시 액티비티 전환
        Button login_btn = (Button)findViewById(R.id.login);
        login_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);
            }
        });


    }
}