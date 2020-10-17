package com.shariful.a1xdeposit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shariful.a1xdeposit.AdapterChat;
import com.shariful.a1xdeposit.MessageActivity;
import com.shariful.a1xdeposit.ModelChat;
import com.shariful.a1xdeposit.R;
import com.shariful.a1xdeposit.User2;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<User2> users2;


    public UserAdapter(Context context, List<User2> users2) {
        this.context = context;
        this.users2 = users2;
    }


    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

                 final User2 user2 = users2.get(position);
                 holder.userNameTv.setText(user2.getuName());


                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(context, MessageActivity.class);
                         intent.putExtra("userid",user2.getId());
                         context.startActivity(intent);

                     }
                 });


    }


    @Override
    public int getItemCount() {
        return users2.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userNameTv;


        public MyViewHolder(View itemView) {
            super(itemView);

            userNameTv = itemView.findViewById(R.id.userNameID);

        }
    }
}
