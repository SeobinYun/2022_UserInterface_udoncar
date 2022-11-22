package com.example.udoncar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "myPageActivity";
    private FirebaseAuth mAuth ;
    Button editBtn;
    Button saveBtn;
    Button logoutBtn;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        editBtn=(Button)findViewById(R.id.edit_btn);
        saveBtn=(Button)findViewById(R.id.save_btn);
        logoutBtn=(Button)findViewById(R.id.logout_btn);
        deleteBtn=(Button)findViewById(R.id.delete_btn);

        editBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }


    public void changeOption(Spinner spinner) {
        if (spinner.isEnabled()) {
            spinner.setEnabled(false);
        } else {
            spinner.setEnabled(true);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_btn:
                signOut();
                break;
            case R.id.delete_btn:
                deleteAccount();
                break;
        }
    }


    // 현재 로그인한 사용자 가져오기, 로그인한 사용자 없으면 null반환
    private void getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }


    private void deleteAccount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
    }

}