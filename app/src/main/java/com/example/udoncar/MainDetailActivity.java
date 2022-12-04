package com.example.udoncar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udoncar.model.Post;
import com.example.udoncar.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainDetailActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private User writeuser;
    private Post post;
    private Intent intent;

    private Button backBtn;
    private Button deleteBtn;
    private Button signupBtn;
    private TextView titleTv, mynameTv, mysexTv, myageTv, positionTv;
    private TextView destTv, dateTv, isrepeatTv;
    private TextView contentTv;
    private TextView ageTv, sexTv;

    private String name, sex, age;

    HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        historyFragment = new HistoryFragment();

        backBtn = (Button)findViewById(R.id.back_btn);
        deleteBtn = (Button) findViewById(R.id.del_btn);
        signupBtn = (Button)findViewById(R.id.signup_btn);

        titleTv = (TextView) findViewById(R.id.title);
        mynameTv = (TextView) findViewById(R.id.nickname);
        mysexTv = (TextView) findViewById(R.id.mysex);
        myageTv = (TextView) findViewById(R.id.myage);
        positionTv = (TextView) findViewById(R.id.position);
        destTv = (TextView) findViewById(R.id.dest);
        dateTv = (TextView) findViewById(R.id.date_tv);
        isrepeatTv = (TextView) findViewById(R.id.isrepeat);
        contentTv = (TextView) findViewById(R.id.content);
        ageTv = (TextView) findViewById(R.id.age);
        sexTv = (TextView) findViewById(R.id.sex);

        //post넘겨받기
        intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");

        titleTv.setText(post.getTitle());
        positionTv.setText(post.getPosition());
        destTv.setText(post.getDest());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
        dateTv.setText(formatter.format(post.getMeetAt()));
        isrepeatTv.setText(post.getIsrepeat());
        contentTv.setText(post.getContent());
        ageTv.setText(post.getoptAge());
        sexTv.setText(post.getoptSex());


        //넘겨받은 post의 user의 정보
        db.collection("users").document(post.getuserId())
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                writeuser = document.toObject(User.class);
                mynameTv.setText(writeuser.getName());
                mysexTv.setText(writeuser.getSex());
                myageTv.setText(writeuser.getAge());
//                name = (String) document.getData().get("name");
//                sex = (String) document.getData().get("sex");
//                age = (String) document.getData().get("age");
//                mynameTv.setText(name);
//                sexTv.setText(sex);
//                ageTv.setText(age);
            }
        });




        //뒤로가기버튼
        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //글삭제버튼
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에서 삭제 (currentid같으면)
                if (post.getuserId() == user.getEmail()){
                    Toast.makeText(getApplicationContext(), "작성자가 맞음", Toast.LENGTH_SHORT).show();
                    db.collection("post").document(post.getpostId()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "삭제 성공!", Toast.LENGTH_SHORT).show();
                                }
                            });

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "작성자가 아닙니다.", Toast.LENGTH_SHORT).show();



            }

        });


        //참가하기 버튼
        signupBtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view){
                //history에 저장
                //histIdS = Integer.toString((int) Math.random()*1000000000);

                Map<String, Object> docData = new HashMap<>();
                docData.put("histId", randomString() );
                docData.put("postId", post.getpostId() );
                docData.put("userId", user.getEmail() );
                db.collection("history").document().set(docData);

                Toast.makeText(getApplicationContext(), "HISTORY에서 확인하세요.", Toast.LENGTH_SHORT).show();


                //history로 이동
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.containers, historyFragment).commit();

//                HistoryFragment historyFragment;
//                historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(R.id.containers);
//                getSupportFragmentManager().beginTransaction().add(historyFragment).commit();


//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                // 프래그먼트매니저를 통해 사용
//                //HistoryFragment historyFragment= new HistoryFragment(); // 객체 생성
//                transaction.replace(R.id.containers, HistoryFragment); //layout, 교체될 layout
//                transaction.commit();

                //getSupportFragmentManager().beginTransaction().add(R.id.containers, HistoryFragment).commit();
//                Intent intent = new Intent(getApplicationContext(), HistoryFragment.class);
//                startActivity(intent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String randomString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}