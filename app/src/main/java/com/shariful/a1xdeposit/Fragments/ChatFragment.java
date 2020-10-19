package com.shariful.a1xdeposit.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.Adapter.ChatAdapter;
import com.shariful.a1xdeposit.Chat;
import com.shariful.a1xdeposit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    FirebaseUser fuser;
    DatabaseReference reference;

    List<Chat> chatList;

    Chat chat;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyViewID);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        chatList =new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      chatList.clear();

                      for (DataSnapshot ds:dataSnapshot.getChildren()){

                           chat =ds.getValue(Chat.class);

                          if(chat.getReceiver().equals(fuser.getUid()) ){
                                    chatList.add(chat);
                          }

                      }

                // readChats();
                chatAdapter =new ChatAdapter(getContext(),chatList);
                recyclerView.setAdapter(chatAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    }

