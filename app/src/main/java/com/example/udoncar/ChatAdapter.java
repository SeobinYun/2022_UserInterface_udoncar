package com.example.udoncar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.ChatData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    private List<ChatData> chatList;
    private String myName;
    public static class ChatHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewMessage;
        public TextView textViewSendtime;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewMessage = itemView.findViewById(R.id.textview_message);
            textViewSendtime = itemView.findViewById(R.id.textview_sendtime);
        }
    }
    // 어댑터에 넣을 데이터set를 매개변수로 하는 생성자 - myName은 내 닉네임 구분하려고
    public ChatAdapter(List<ChatData> chatList, String myName){
        this.chatList = chatList;
        this.myName = myName;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_chat, parent, false); //어떤 레이아웃을 쓸지
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatData chat = chatDataList.get(position); //position을 인덱스로 객체 하나 가져옴
        holder.textViewName.setText(chat.getName()); // 가져온 chat객체에서 데이터 뽑아내서 holder에 세팅
        holder.textViewMessage.setText(chat.getMessage());
        holder.textViewSendtime.setText(chat.getSendtime());

        //닉네임이 내 닉네임이면 오른쪽으로 정렬한다.
        if(chat.getName().equals(this.myName)){
            holder.textViewMessage.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.textViewSendtime.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
    }

    @Override
    public int getItemCount() {
        return chatDataList.size();
    }
}
