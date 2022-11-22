package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        //뒤로가기버튼 클릭 시 메인화면으로 이동
        Button backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //글삭제버튼 클릭 시 DB에서 글 삭제 후 메인화면으로 이동

        //참가하기버튼 클릭 시 채팅화면으로 이동
        Button signupBtn = (Button)findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ChattingActivity.class);
                startActivity(intent);
            }
        });
    }
}