package com.example.udoncar;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.History;
import com.example.udoncar.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<History> historyList;
    Context context;

    public HistoryAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new MainHolder(view);
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Post post;
    private String meetDateString;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = historyList.get(position);
 /*       ((MainHolder) holder).textViewTitle.setText(history.gethistId());
        ((MainHolder) holder).textViewDest.setText(history.getpostId());
        ((MainHolder) holder).textViewDest.setText(history.getuserId());

  */
        DocumentReference docRef = db.collection("post").document(history.getpostId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Post post = document.toObject(Post.class);
                        //post.setMeetAt((Date)document.getData().get("meetAt"));
                        ((MainHolder) holder).textViewTitle.setText((String)document.getData().get("title"));
                        ((MainHolder) holder).textViewDest.setText((String)document.getData().get("dest"));
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
                        meetDateString = formatter.format((Date) post.getMeetAt());
                        ((MainHolder) holder).textViewTime.setText(meetDateString);

                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                }
            }
        });




//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
//        meetDateString = formatter.format(post.getMeetAt());
//        ((MainHolder) holder).textViewTime.setText(meetDateString);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //histroy -> chat
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("history", history);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList == null ? 0 : historyList.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDest;
        public TextView textViewTime;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.histitle_tv);
            textViewDest = itemView.findViewById(R.id.hisdest_tv);
            textViewTime = itemView.findViewById(R.id.histime_tv);
        }
    }
}