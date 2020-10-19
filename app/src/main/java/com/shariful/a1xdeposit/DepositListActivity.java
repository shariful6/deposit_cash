package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DepositListActivity extends AppCompatActivity {

    RecyclerView recyclerView_deposit;

    ArrayList<ModelDeposit> depositList;

    CustomAdapter adapter;

    DatabaseReference databaseReference;

    String intentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_list);

        recyclerView_deposit =findViewById(R.id.recyclerView_deposit);

        recyclerView_deposit.setLayoutManager(new LinearLayoutManager(this));
        depositList = new ArrayList<ModelDeposit>();

        adapter = new CustomAdapter(DepositListActivity.this,depositList);
        recyclerView_deposit.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Deposit");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                depositList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        ModelDeposit modelDeposit = ds.getValue(ModelDeposit.class);
                        //   ModelDeposit modelDeposit =new ModelDeposit(pId,amount,currency_type,paymentMethod,phone,transactionId,userID,status);
                        depositList.add(modelDeposit);

                        adapter = new CustomAdapter(DepositListActivity.this,depositList);
                        recyclerView_deposit.setAdapter(adapter);

                    }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DepositListActivity.this, "Oops something is wrong!", Toast.LENGTH_SHORT).show();

            }
        });



    }

}
