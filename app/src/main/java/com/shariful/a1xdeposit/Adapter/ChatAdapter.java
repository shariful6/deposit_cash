package com.shariful.a1xdeposit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shariful.a1xdeposit.Chat;
import com.shariful.a1xdeposit.MessageActivity;
import com.shariful.a1xdeposit.R;
import com.shariful.a1xdeposit.User2;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context context;
    private List<Chat> chatList;


    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Chat chat = chatList.get(position);
        holder.userNameTv.setText(chat.getuName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid",chat.getSender());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userNameTv;


        public MyViewHolder(View itemView) {
            super(itemView);

            userNameTv = itemView.findViewById(R.id.userNameID);

        }
    }
}
