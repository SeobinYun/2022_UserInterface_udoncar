package com.example.udoncar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.Chat;

import java.text.SimpleDateFormat;
import java.util.List;

public class NumDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> peopleList;

    public NumDialogAdapter(List<String> peopleList){
        this.peopleList = peopleList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_numpeople, parent, false);
        return new NumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NumHolder) holder).textViewNumPeople.setText(peopleList.get(position));
    }

    @Override
    public int getItemCount() {
        return peopleList == null ? 0 : peopleList.size();
    }

    public static class NumHolder extends RecyclerView.ViewHolder {
        public TextView textViewNumPeople;
        public NumHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumPeople = itemView.findViewById(R.id.textview_numpeople);
        }
    }
}
