package com.example.udoncar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        holder.main_title.setText(arrayList.get(position).getMaintitle());
        holder.main_path.setText(arrayList.get(position).getMainpath());
        holder.main_date.setText(arrayList.get(position).getMaindate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //짧게 클릭했을때 -> 화면전환
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView main_title;
        protected TextView main_path;
        protected TextView main_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.main_title = (TextView) itemView.findViewById(R.id.main_title);
            this.main_path = (TextView) itemView.findViewById(R.id.main_path);
            this.main_date = (TextView) itemView.findViewById(R.id.main_date);

        }
    }
}
