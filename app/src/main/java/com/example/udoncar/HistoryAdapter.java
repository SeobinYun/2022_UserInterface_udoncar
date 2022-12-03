package com.example.udoncar;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.History;

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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = historyList.get(position);
        ((MainHolder) holder).textViewTitle.setText(history.gethistId());
        ((MainHolder) holder).textViewDest.setText(history.getpostId());
        ((MainHolder) holder).textViewTime.setText(history.getuserId());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
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