package com.shariful.a1xdeposit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyViewHolder> {

    Context context;
    ArrayList<ModelChat> chatList;

    public AdapterChat(Context context, ArrayList<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public AdapterChat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.MyViewHolder holder, int position) {

        String message =chatList.get(position).getMessage();

        holder.messageTv.setText(message);
       // holder.timeTv.setText(dateTime);


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView messageTv,timeTv;


        public MyViewHolder(View itemView) {
            super(itemView);



        }
    }
}
