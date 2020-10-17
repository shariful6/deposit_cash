package com.shariful.a1xdeposit;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WithdrawListActivity extends AppCompatActivity {

    RecyclerView recyclerView_withdraw;

    ArrayList<ModelWithdraw> withdrawList;

    AdapterWithdraw adapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_list);


        recyclerView_withdraw =findViewById(R.id.recyclerView_withdrawID);

        recyclerView_withdraw.setLayoutManager(new LinearLayoutManager(this));

        withdrawList = new ArrayList<ModelWithdraw>();



        databaseReference = FirebaseDatabase.getInstance().getReference("Withdraw");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                withdrawList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                   ModelWithdraw modelWithdraw = dataSnapshot1.getValue(ModelWithdraw.class);
                   // ModelWithdraw modelWithdraw =new ModelWithdraw(pId,userId,code,ammount,payment_method,currency_type,phone);
                    withdrawList.add(modelWithdraw);

                    adapter = new AdapterWithdraw(WithdrawListActivity.this,withdrawList);
                    recyclerView_withdraw.setAdapter(adapter);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(WithdrawListActivity.this, "Oops something is wrong!", Toast.LENGTH_SHORT).show();

            }
        });



    }

}
