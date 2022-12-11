package com.example.udoncar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udoncar.model.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Chat> chatList = new ArrayList<Chat>();
    private String myName;

    // 어댑터에 넣을 데이터set를 매개변수로 하는 생성자 - myName은 내 닉네임 구분하려고
    public ChatAdapter(List<Chat> chatList, String myName){
        this.chatList = chatList;
        this.myName = myName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // viewType에 따라 다른 뷰홀더 return할 수 있도록
        if (viewType == ViewCode.TYPE_RECEIVED_MESSAGE){
            view = inflater.inflate(R.layout.item_chat, parent, false); //어떤 레이아웃을 쓸지
            return new ChatHolder(view);
        }
        else if (viewType == ViewCode.TYPE_SENT_MESSAGE){
            view = inflater.inflate(R.layout.item_my_chat, parent, false); //어떤 레이아웃을 쓸지
            return new MyChatHolder(view);
        }
        else{
            System.out.println("VIEWTYPE_ERROR");
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position); //position을 인덱스로 객체 하나 가져옴
        if(holder instanceof ChatHolder){
            ((ChatHolder) holder).textViewName.setText(chat.getName()); // 가져온 chat객체에서 데이터 뽑아내서 holder에 세팅
            ((ChatHolder) holder).textViewMessage.setText(chat.getMessage());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");//형식 지정
            String formatTime = simpleDateFormat.format(chat.getcreateAt());
            ((ChatHolder) holder).textViewSendtime.setText(formatTime);
        }
        else {
            ((MyChatHolder) holder).textViewMyMessage.setText(chat.getMessage());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");//형식 지정
            String formatTime = simpleDateFormat.format(chat.getcreateAt());
            ((MyChatHolder) holder).textViewMySendtime.setText(formatTime);
        }
    }

    @Override
    public int getItemCount() {
        return chatList == null ? 0 : chatList.size();
    }

    public void addChat(Chat chat){
        if(chat != null){
            chatList.add(chat);
            notifyItemInserted(chatList.size()-1); // 갱신할때마다 알려줌
        }
    }
    // view type
    public static class ViewCode {
        private static final int TYPE_SENT_MESSAGE = 101;
        private static final int TYPE_RECEIVED_MESSAGE = 102;
    }

    @Override
    public int getItemViewType(int position) {
        String chatName = chatList.get(position).getName();
        if (chatName.equals(myName)){
            return ViewCode.TYPE_SENT_MESSAGE;
        } else {
            return ViewCode.TYPE_RECEIVED_MESSAGE;
        }
    }

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
    public static class MyChatHolder extends RecyclerView.ViewHolder {
        public TextView textViewMyMessage;
        public TextView textViewMySendtime;
        public MyChatHolder(@NonNull View itemView) {
            super(itemView);
            textViewMyMessage = itemView.findViewById(R.id.textview_mymessage);
            textViewMySendtime = itemView.findViewById(R.id.textview_mysendtime);
        }
    }
}