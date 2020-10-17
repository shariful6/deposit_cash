package com.shariful.a1xdeposit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.shariful.a1xdeposit.Fragments.ChatFragment;
import com.shariful.a1xdeposit.Fragments.UserFragment;


public class ChatActivity extends AppCompatActivity {

    TextView chatBtn,userBtn;
    ActionBar actionBar;
    LinearLayout linearLayout;

    String intentValue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        actionBar =getSupportActionBar();



        chatBtn =findViewById(R.id.chatTvBtnID);
        userBtn =findViewById(R.id.userTvBtnID);
        linearLayout = findViewById(R.id.linLayoutID);

        intentValue = getIntent().getStringExtra("key");

         loadUserFragment();

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actionBar.setTitle("ChatsList");
                ChatFragment chatFragment = new ChatFragment();
                FragmentTransaction ft1 =getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.containerID,chatFragment,"");
                ft1.commit();

            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(intentValue.equals("2")){
                    actionBar.setTitle("Admin");
                    bundle.putString("key","2");
                }
                else {
                    actionBar.setTitle("UserList");
                    bundle.putString("key","1");
                }

                UserFragment userFragment = new UserFragment();
                userFragment.setArguments(bundle);
                FragmentTransaction ft1 =getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.containerID,userFragment,"");
                ft1.commit();
            }
        });



    }

    private void loadUserFragment() {

        Bundle bundle = new Bundle();
        if(intentValue.equals("2")){
            linearLayout.setVisibility(View.GONE);
            actionBar.setTitle("Admin");
            bundle.putString("key","2");
        }
        else {

            actionBar.setTitle("UserList");
            bundle.putString("key","1");
        }

        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(bundle);
        FragmentTransaction ft1 =getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.containerID,userFragment,"");
        ft1.commit();
    }


}


