package com.shariful.a1xdeposit.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shariful.a1xdeposit.Chat;
import com.shariful.a1xdeposit.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    public static  final  int MSG_TYPE_LEFT=0;
    public static  final  int MSG_TYPE_RIGHT=1;

    private Context context;
    private List<Chat> chatList;
    FirebaseUser fuser;


    public MessageAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false));
        }
        else {

            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {

         Chat chat = chatList.get(position);
         holder.showMessage.setText(chat.getMessage());

         String pId= chatList.get(position).getpId();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pId));
        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        holder.timeTV.setText(pTime);

    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView showMessage,timeTV;


        public MyViewHolder(View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessageID);
            timeTV = itemView.findViewById(R.id.timeTVID_message);



        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fuser.getUid())){

            return MSG_TYPE_RIGHT;
        }
        else {

            return MSG_TYPE_LEFT;
        }



    }


}
