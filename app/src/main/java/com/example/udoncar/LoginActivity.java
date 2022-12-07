package com.example.udoncar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "loginActivity";
    private FirebaseAuth mAuth;
    private EditText idEdittext;
    private EditText pwEdittext;
    private Button loginBtn;
    public Pattern emailPattern = Patterns.EMAIL_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        idEdittext = (EditText) findViewById(R.id.id_edittext);
        pwEdittext = (EditText) findViewById(R.id.pw_edittext);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edittextToString(pwEdittext).equals("") | edittextToString(idEdittext).equals("")){
                    Toast.makeText(LoginActivity.this, "필수 정보를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else{
                Log.d(TAG, "온클릭옴");
                loginUser(edittextToString(idEdittext), edittextToString(pwEdittext));
            }}
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    // Edittext의 입력값을 String으로 리턴하는 함수
    private String edittextToString(EditText sentence) {
        return sentence.getText().toString();
    }

    // 로그인 함수
    private void loginUser(String email, String password) {
        Log.d(TAG, "loginUser 진입");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete 진입");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            if (!emailPattern.matcher(edittextToString(idEdittext)).matches()) {
                                Toast.makeText(LoginActivity.this, "ID를 이메일 형식으로 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "ID와 PW가 일치하지 않습니다. 다시 시도해주세요.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void reload() {
    }
}
