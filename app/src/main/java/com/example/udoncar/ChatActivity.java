package com.example.udoncar;

import static androidx.constraintlayout.widget.StateSet.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.udoncar.model.History;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
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

    private FirebaseUser user;
    private FirebaseFirestore db;
    private String userName;

    private History history;
    private String postTitle;
    List<String> users;
    private String addDocId;

    Dialog numDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent historyIntent = getIntent();
        history = (History) historyIntent.getSerializableExtra("history");

        //view 세팅
        titleTextView = findViewById(R.id.title_textview);
        numPeopleBtn = findViewById(R.id.num_people_btn);
        chatEditText = findViewById(R.id.chat_edittext);
        chatSendBtn = findViewById(R.id.chat_send_btn);

        //firebase 관련
        db = FirebaseFirestore.getInstance();

        // db에서 정보 가져오기
        chatList = new ArrayList<Chat>();
        chatList = getChatList();
        getUserName();

        //recyclerview 세팅
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        chatLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);
        chatAdapter = new ChatAdapter(chatList, userName);
        chatRecyclerView.setAdapter(chatAdapter);


        // send 버튼 리스너
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference chatRef = db.collection("history").document(history.gethistId()).collection("msg").document();
                String msg = chatEditText.getText().toString();
                if(msg.length() != 0){
                    chatEditText.setText(null);
                    Chat chat = new Chat();
                    chat.setMessage(msg);
                    chat.setUserId(user.getEmail());
                    chat.setName(userName);;
                    chat.setcreateAt(new Date());
                    chatRef.set(chat);
                    addDocId = chatRef.getId();
                }
            }
        });
        // 변화 생기면 현재 시간과 같거나 빠른 채팅들 불러오기
        Timestamp nowTime = Timestamp.now();
        db.collection("history").document(history.gethistId()).collection("msg")
                .whereGreaterThanOrEqualTo("createAt", nowTime)
                .orderBy("createAt", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New chat: " + dc.getDocument().getData());
                                    Chat chat = dc.getDocument().toObject(Chat.class);
                                    ((ChatAdapter) chatAdapter).addChat(chat);
                                    break;
                            }
                        }

                    }
                });


        //title 넣기
        db.collection("post").document(history.getpostId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        postTitle = documentSnapshot.getData().get("title").toString();
                        titleTextView.setText(postTitle);
                    }
                });

        //인원수 버튼
        users = new ArrayList<String>();
        users = history.getUsersId();
        setNumUsers(users.size());

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


    public void getUserName() {
        //user의 이메일 정보로 유저 정보 db에서 가져옴
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            // 닉네임 불러오기
                            userName = document.getData().get("name").toString();
                            chatAdapter = new ChatAdapter(chatList, userName);
                            chatRecyclerView.setAdapter(chatAdapter);
                        }
                    }
                });
    }
    //처음 입장 시 이전 채팅들 불러오기
    public List<Chat> getChatList(){
        chatList = new ArrayList<Chat>();
        db.collection("history").document(history.gethistId()).collection("msg")
                .orderBy("createAt", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Chat chat = document.toObject(Chat.class);
                                chatList.add(chat);
                                chatRecyclerView.setAdapter(chatAdapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return chatList;
    }
}