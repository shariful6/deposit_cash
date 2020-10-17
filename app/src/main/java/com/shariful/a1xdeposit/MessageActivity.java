package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.Adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    FirebaseUser fuser;
    DatabaseReference reference;

    Intent intent;

    ImageButton sendMessageBtn;
    EditText messageET;

    MessageAdapter messageAdapter;
    List<Chat> mchats;
    RecyclerView recyclerView;

    User2 user2;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView =findViewById(R.id.recyclerView_Message);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        sendMessageBtn = findViewById(R.id.send_messageBtn);
        messageET = findViewById(R.id.messageET);


        intent = getIntent();
        final String userid =intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        retriveMyUserName();

        reference = FirebaseDatabase.getInstance().getReference("UserList").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 user2 =dataSnapshot.getValue(User2.class);
                setTitle(user2.getuName());
                readMessage(fuser.getUid(),userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = messageET.getText().toString();
                if (!messageTxt.equals("")){
                    sendMessage(fuser.getUid(),userid,messageTxt);////////////////////////////
                    messageET.setText("");
                }
                else{
                    Toast.makeText(MessageActivity.this, "Write Something ....", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    private  void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap =new HashMap<>();

        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("uName",name); ////////////////////my name
        reference.child("Chats").push().setValue(hashMap);


    }

   private void readMessage(final String myid, final String userid) {

       mchats = new ArrayList<>();

       DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference("Chats");

       dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               mchats.clear();
               for (DataSnapshot ds: dataSnapshot.getChildren())
               {
                   Chat chat = ds.getValue(Chat.class);

                   if (chat.getReceiver().equals(myid)&&chat.getSender().equals(userid)||chat.getReceiver().equals(userid)&&chat.getSender().equals(myid))
                   {
                       mchats.add(chat);
                   }
                   messageAdapter = new MessageAdapter(MessageActivity.this,mchats);
                   messageAdapter.notifyDataSetChanged();
                   recyclerView.setAdapter(messageAdapter);

               }


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {



           }
       });

    }

   public void retriveMyUserName(){

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("UserList");
        final String myUid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    user2 = ds.getValue(User2.class);

                    if (myUid.equals(user2.getId())){

                        name =user2.getuName();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
