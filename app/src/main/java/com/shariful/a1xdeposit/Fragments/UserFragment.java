package com.shariful.a1xdeposit.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.Adapter.UserAdapter;
import com.shariful.a1xdeposit.R;
import com.shariful.a1xdeposit.User2;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    List<User2> userList;

     String rValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rValue = getArguments().getString("key");

        View view =inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView =view.findViewById(R.id.recyclerView_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList =new ArrayList<>();



        readUserList();

        return view;
    }

    private void readUserList() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    User2 user2 =ds.getValue(User2.class);

                    if (!user2.getId().equals(firebaseUser.getUid())){

                        if (rValue.equals("1")){
                            userList.add(user2);
                        }
                        if (rValue.equals("2")){
                            if (user2.getMobile().equals("admin")){
                                userList.add(user2);
                            }

                        }

                    }

                }
                userAdapter =new UserAdapter(getContext(),userList);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
