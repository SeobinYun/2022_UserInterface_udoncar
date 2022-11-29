package com.example.udoncar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.History;
import com.example.udoncar.model.Post;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> postList;

    public MainAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = postList.get(position);
        ((MainHolder) holder).textViewTitle.setText(post.getTitle());
        ((MainHolder) holder).textViewDest.setText(post.getDest());
        ((MainHolder) holder).textViewTime.setText((CharSequence) post.getMeetAt());

//        holder.itemView.setTag(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //짧게 클릭했을때 -> 화면전환
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDest;
        public TextView textViewTime;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.maintitle_tv);
            textViewDest = itemView.findViewById(R.id.maindest_tv);
            textViewTime = itemView.findViewById(R.id.maintime_tv);
        }
    }
}