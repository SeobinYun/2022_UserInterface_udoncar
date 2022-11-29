package com.example.udoncar;

import static androidx.constraintlayout.widget.StateSet.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.udoncar.model.Chat;
import com.example.udoncar.model.ChatUserList;
import com.example.udoncar.model.History;
import com.example.udoncar.model.Post;
import com.example.udoncar.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    public static ChatActivity chatActivity;
    private RecyclerView chatRecyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager chatLayoutManager;
    private List<Chat> chatList;

    private RecyclerView numRecyclerView;
    private RecyclerView.Adapter numDialogAdapter;
    private RecyclerView.LayoutManager numLayoutManager;

    private TextView titleTextView;
    private Button numPeopleBtn;
    private EditText chatEditText;
    private Button chatSendBtn;

    private FirebaseFirestore db;
    private String email;
    private User currentUser;

    private History history;
    List<String> users;

    Dialog numDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatActivity = this;

        //임시 history
        history.sethistId("3RLVl6M40SX7yUtmqQMK");
        history.setpostId("Nrzka3ucS3mZL1tIhQkW");

        //recyclerview 세팅
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        chatRecyclerView.setHasFixedSize(true); // 크기 고정
        chatLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);


        //view 세팅
        titleTextView = findViewById(R.id.title_textview);
        numPeopleBtn = findViewById(R.id.num_people_btn);
        chatEditText = findViewById(R.id.chat_edittext);
        chatSendBtn = findViewById(R.id.chat_send_btn);

        //firebase 관련
        db = FirebaseFirestore.getInstance();

        //user의 이메일 정보로 유저 정보 db에서 가져옴
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }

        DocumentReference currentuserRef = db.collection("users").document(email);
        currentuserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
            }
        });

        /*FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings.Builder settings = new FirebaseFirestoreSettings.Builder();
        settings.setTimestampsInSnapshotsEnabled(true);
        database.setFirestoreSettings(settings.build());*/

        // db에서 채팅 가져오기
        /*chatList = new ArrayList<Chat>();
        db.collection("history").document(histId).collection("msg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Chat chat = document.toObject(Chat.class);
                                chatList.add(chat);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/

        chatAdapter = new ChatAdapter(chatList, currentUser.getName());
        chatRecyclerView.setAdapter(chatAdapter);

        CollectionReference chatRef = db.collection("msg");
        // send 버튼 리스너
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = chatEditText.getText().toString();
                if(msg != null){
                    Chat chat = new Chat();
                    chat.setMessage(msg);
                    chat.setUserId(currentUser.getId());
                    chat.setName(currentUser.getName());;
                    //timestamp세팅........
                    chatRef.add(chat);
                }
            }
        });
        db.collection("history").document(history.gethistId()).collection("msg")
                .orderBy("createAt", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            Chat chat = doc.toObject(Chat.class);
                            chatList.add(chat);
                        }
                    }
                });


        //title 넣기
        db.collection("post").document(history.getpostId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Post post = documentSnapshot.toObject(Post.class);
                        titleTextView.setText(post.getTitle());
                    }
                });

        //인원수 버튼
        db.collection("chatUserList")
                .whereEqualTo("hist_id", history.gethistId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : snapshots) {
                            ChatUserList chatUserList = doc.toObject(ChatUserList.class);
                            users = chatUserList.getUsersId();
                            setNumUsers(users.size());
                        }
                    }
                });

        numPeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNum();
            }
        });


    }
    private void showDialogNum() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        numDialog = new Dialog(this);

        display.getRealSize(size);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        LayoutInflater inf = getLayoutInflater();
        View dialogView = inf.inflate(R.layout.dialog_chat_num_people, null);
        // Dialog layout 선언

        lp.copyFrom(numDialog.getWindow().getAttributes());
        int width = size.x;
        lp.width = width * 80 / 100; // 사용자 화면의 80%
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 높이는 내용 전체 높이만큼
        numDialog.setContentView(dialogView); // Dialog에 선언했던 layout 적용
        numDialog.setCanceledOnTouchOutside(true); // 외부 touch 시 Dialog 종료
        numDialog.getWindow().setAttributes(lp); // 지정한 너비, 높이 값 Dialog에 적용
        /*
        다음 4줄의 코드는 RecyclerView를 정의하기 위한 View, Adapter선언 코드이다.
        1. RecyclerView id 등록
        2. 수직방향으로 보여줄 예정이므로 LinearLayoutManager 등록
           2차원이면 GridLayoutManager 등 다른 Layout을 선택
        3. adapter에 topic Array 넘겨서 출력되게끔 전달
        4. adapter 적용
        */
        // 다이얼로그 리사이클러뷰
        numRecyclerView = (RecyclerView) dialogView.findViewById(R.id.num_people_recycler);
        numRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        numDialogAdapter = new NumDialogAdapter(users);
        numRecyclerView.setAdapter(numDialogAdapter);
        numDialog.show(); // Dialog 출력
    }
    //인원수 세팅 함수
    public void setNumUsers(int numUsers){
        numPeopleBtn.setText(String.format("인원수 %d명", numUsers));
    }
}