package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.udoncar.model.ChatData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager chatLayoutManager;
    private List<ChatData> chatList;

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

        chatAdapter = new ChatAdapter(chatList);
        chatRecyclerView.setAdapter(chatAdapter);
    }
}