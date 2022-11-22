package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.udoncar.model.Chat;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager chatLayoutManager;
    private List<Chat> chatList;

    private String name = "min";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        chatRecyclerView.setHasFixedSize(true); // 뭐임?

        chatLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);

        chatAdapter = new ChatAdapter(chatList, name);
        chatRecyclerView.setAdapter(chatAdapter);
    }
}