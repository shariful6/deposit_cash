package com.shariful.a1xdeposit.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.R;

import java.util.ArrayList;

public class ClientDepoListActivity extends AppCompatActivity {

    RecyclerView recyclerView_deposit2;

    ArrayList<ClientModelDeposit> depositList2;

    AdapterClientDepo adapter2;

    DatabaseReference databaseReference2;

    String intentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_depo_list);

        recyclerView_deposit2 =findViewById(R.id.recyclerView_deposit2);
        recyclerView_deposit2.setLayoutManager(new LinearLayoutManager(this));
        depositList2 = new ArrayList<ClientModelDeposit>();

        adapter2 = new AdapterClientDepo(ClientDepoListActivity.this,depositList2);
        recyclerView_deposit2.setAdapter(adapter2);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("Deposit");
        databaseReference2.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                depositList2.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    ClientModelDeposit clientModelDeposit = ds.getValue(ClientModelDeposit.class);
                    //   ModelDeposit modelDeposit =new ModelDeposit(pId,amount,currency_type,paymentMethod,phone,transactionId,userID,status);
                    depositList2.add(clientModelDeposit);

                    adapter2 = new AdapterClientDepo(ClientDepoListActivity.this,depositList2);
                    recyclerView_deposit2.setAdapter(adapter2);

                }

                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ClientDepoListActivity.this, "Oops something is wrong!", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
