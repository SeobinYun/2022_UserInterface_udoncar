package com.example.udoncar;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.example.udoncar.model.User;
import com.example.udoncar.model.Chat;
import com.example.udoncar.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.example.udoncar.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    long initTime;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    HomeFragment homeFragment;
    WriteFragment writeFragment;
    HistoryFragment historyFragment;
    MypageFragment mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkCurrentUser();

        homeFragment = new HomeFragment();
        writeFragment = new WriteFragment();
        historyFragment = new HistoryFragment();
        mypageFragment = new MypageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_menu);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.write:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, writeFragment).commit();
                        return true;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, historyFragment).commit();
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, mypageFragment).commit();
                        return true;
                }
                return false;
            }
        });

        navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
    }

    public void checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String email = user.getEmail();
        } else {
            // No user is signed in
        }
    }

    // 로그아웃
    public void signOut(FirebaseUser user) {
        FirebaseAuth.getInstance().signOut();
        Log.d(TAG, "User account logout");
        Toast.makeText(MainActivity.this, "로그아웃 성공!", Toast.LENGTH_LONG).show();
        // 시작화면으로 돌아가는 코드
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
        finish();
    }

    // 회원탈퇴
    public void deleteUser(FirebaseUser user) {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // firestore user 부분 documnet 삭제
                            db.collection("users").document(user.getEmail())
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            FirebaseAuth.getInstance().signOut();
                                            // 시작화면으로 돌아가는 코드
                                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });
                            Log.d(TAG, "User account deleted.");

                            // firestore post 부분 document 삭제
                            db.collection("post").whereEqualTo("user_id", user.getEmail())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                Log.d(TAG, "task: " + task);

                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                    Log.d(TAG, document.getData().toString());
                                                    document.getReference().delete();
                                                }
                                            }
                                            else{
                                                Log.d(TAG, "Error getting document: ", task.getException());
                                            }
                                        }
                                    });

                            Toast.makeText(MainActivity.this, "정상적으로 탈퇴처리 되었습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "DB deletion failed.");
                        }
                    }
                });
    }
    private void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {showToast("종료할려면 한번 더 누르세요.");
                initTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ArrayList<Post> postList = (ArrayList<Post>) data.getSerializableExtra("postListD");
            System.out.println("성공" + postList.size());
            Bundle bundle = new Bundle();
            bundle.putSerializable("postListD", (ArrayList<Post>) postList);
            homeFragment = new HomeFragment();
            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
        }
    }

}