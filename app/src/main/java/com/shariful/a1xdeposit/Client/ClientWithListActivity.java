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
import com.shariful.a1xdeposit.AdapterWithdraw;
import com.shariful.a1xdeposit.ModelWithdraw;
import com.shariful.a1xdeposit.R;
import com.shariful.a1xdeposit.WithdrawListActivity;

import java.util.ArrayList;

public class ClientWithListActivity extends AppCompatActivity {

    RecyclerView recyclerView_withdraw2;

    ArrayList<ClientModelWithdraw> withdrawList2;

    AdapterClientWithdraw adapter2;

    DatabaseReference databaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_with_list);

        recyclerView_withdraw2 =findViewById(R.id.recyclerView_withdrawID2);

        recyclerView_withdraw2.setLayoutManager(new LinearLayoutManager(this));

        withdrawList2 = new ArrayList<ClientModelWithdraw>();



        databaseReference2 = FirebaseDatabase.getInstance().getReference("Withdraw");
        databaseReference2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                withdrawList2.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    ClientModelWithdraw clientModelWithdraw= dataSnapshot1.getValue(ClientModelWithdraw.class);
                    // ModelWithdraw modelWithdraw =new ModelWithdraw(pId,userId,code,ammount,payment_method,currency_type,phone);
                    withdrawList2.add(clientModelWithdraw);

                    adapter2 = new AdapterClientWithdraw(ClientWithListActivity.this,withdrawList2);
                    recyclerView_withdraw2.setAdapter(adapter2);
                }

                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ClientWithListActivity.this, "Oops something is wrong!", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
