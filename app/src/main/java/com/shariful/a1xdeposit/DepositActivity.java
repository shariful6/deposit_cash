package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.Client.ClientDepoListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepositActivity extends AppCompatActivity {

    String[] payment_method;
    String[] currency_method;
    private Spinner spinner,spinner_sp;

    Button coppyBtn,depositBtn;
    EditText userID_ET,ammountET,phoneNumberET,transactionET,messageET;

    String pushId;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    List<ModelTr> trList;
    ModelTr tr;

    String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        myUid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        trList = new ArrayList<>();
        tr=new ModelTr();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
        pushId = myRef.push().getKey();

        payment_method =getResources().getStringArray(R.array.payment_method);
        currency_method =getResources().getStringArray(R.array.currency);

        coppyBtn= findViewById(R.id.coppyBtnID);
        depositBtn = findViewById(R.id.submitBtnID);
        spinner = findViewById(R.id.spinner_ID);
        spinner_sp =findViewById(R.id.spinner_currencyID);
        userID_ET =findViewById(R.id.userId_id);
        ammountET =findViewById(R.id.amountID);
        phoneNumberET =findViewById(R.id.numberID);
        transactionET = findViewById(R.id.transactionID);
        messageET =findViewById(R.id.messageID);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textview_sampleID,payment_method);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textview_sampleID,currency_method);
        spinner.setAdapter(arrayAdapter);
        spinner_sp.setAdapter(adapter);
        //

        retriveTransactionList();

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDeposit();
            }
        });



    }

    private void retriveTransactionList() {


        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User").child("transactionId");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trList.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    String id = ds.child("id").getValue(String.class);
                    tr =new ModelTr(id);
                    trList.add(tr);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DepositActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void submitDeposit() {

        String user_id= userID_ET.getText().toString();
        String amount =ammountET.getText().toString();
        String phone = phoneNumberET.getText().toString();

        String payment_method = spinner.getSelectedItem().toString();
        String currency_type = spinner_sp.getSelectedItem().toString();

        String transaction_id = transactionET.getText().toString();
        String message = messageET.getText().toString();


        if (TextUtils.isEmpty(user_id)){
            userID_ET.setError("Enter User Id");
            userID_ET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(amount)){
            ammountET.setError("Enter Amount");
            ammountET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)){
            phoneNumberET.setError("Enter Phone Number");
            phoneNumberET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(transaction_id)){
            transactionET.setError("Enter Transaction Id");
            transactionET.requestFocus();
            return;
        }

        else{

            final String timeStamp = String.valueOf(System.currentTimeMillis());
             String filePathAndName = "Deposit/" + "deposit_" + timeStamp;

            HashMap<Object,String> hashMap = new HashMap<>();
            hashMap.put("userId",user_id);
            hashMap.put("amount",amount);
            hashMap.put("phone",phone);
            hashMap.put("payment_method",payment_method);
            hashMap.put("currency_type",currency_type);
            hashMap.put("transactionId",transaction_id);
            hashMap.put("message",message);
            hashMap.put("pId",timeStamp);

            hashMap.put("myUid",myUid);

            if (tr.getId().contains(transaction_id)){
                hashMap.put("status","Aproved");
            }
            else{
                hashMap.put("status","Pending");
            }

            // hashMap.put("request_for","deposit");

            FirebaseDatabase database =FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Deposit");
            //put data within hasmap in database
            reference.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DepositActivity.this, "Deposit Request Submitted !!", Toast.LENGTH_SHORT).show();

                            userID_ET.setText("");
                            ammountET.setText("");
                            phoneNumberET.setText("");
                            transactionET.setText("");
                            messageET.setText("");

                            Intent intent =new Intent(DepositActivity.this, ClientDepoListActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DepositActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });



        }

    }




}
