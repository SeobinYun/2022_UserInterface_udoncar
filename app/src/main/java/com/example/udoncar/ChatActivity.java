package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.udoncar.model.Chat;
import com.example.udoncar.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager chatLayoutManager;
    private List<Chat> chatList;

    private TextView titleTextView;
    private Button numPeopleBtn;
    private EditText chatEditText;
    private Button chatSendBtn;

    private FirebaseFirestore db;
    private String email;
    private User curruntUser;
    private String name = "min";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        //db설정
        db = FirebaseFirestore.getInstance();
        //user의 이메일 정보로 유저 정보 db에서 가져옴
        DocumentReference currentuserRef = db.collection("users").document(email);
        currentuserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curruntUser = documentSnapshot.toObject(User.class);
            }
        });

        titleTextView = findViewById(R.id.title_textview);
        numPeopleBtn = findViewById(R.id.num_people_btn);
        chatEditText = findViewById(R.id.chat_edittext);
        chatSendBtn = findViewById(R.id.chat_send_btn);

        DocumentReference chatRef = db.collection("msg").document();
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = chatEditText.getText().toString();
                Chat chat = new Chat();
                chat.setMessage(msg);
                chat.setUserId(curruntUser.getId());
                chat.setName(curruntUser.getName());
                chat.setSendtime(Timestamp.now());
                chatRef.set(chat);
            }
        });
        // Access a Cloud Firestore instance from your Activity


        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        chatRecyclerView.setHasFixedSize(true); // 뭐임?

        chatLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);

        chatAdapter = new ChatAdapter(chatList, name);
        chatRecyclerView.setAdapter(chatAdapter);
    }
}