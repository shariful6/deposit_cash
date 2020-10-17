package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    String[] fundType;
    Spinner spinner_fund;

    EditText noticeET,phoneET,transactionET,roketET,nagatET;

    Button fundChangBtn,depositListBtn,withdrawListBtn,publish_noticeBtn,changeNoBtn,storeBtn,inboxBtn,roketCngBtn,nagatCngBtn;

    String pushId;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
        pushId = myRef.push().getKey();

        fundType =getResources().getStringArray(R.array.fundType);

        spinner_fund = findViewById(R.id.fundSpinnerID);
        fundChangBtn = findViewById(R.id.fundChangeBtnID);
        depositListBtn =findViewById(R.id.depositListBtnID);
        withdrawListBtn =findViewById(R.id.withdrawListBtnID);
        publish_noticeBtn =findViewById(R.id.publishNoticeBtnID);
        changeNoBtn = findViewById(R.id.phoneChangeBtnID);
        noticeET = findViewById(R.id.noticeET_id);
        phoneET =findViewById(R.id.phoneChangeET_ID);
        storeBtn = findViewById(R.id.storeBtnID);
        transactionET =findViewById(R.id.trET_ID);
        inboxBtn =findViewById(R.id.inboxBtnID);
        roketET =findViewById(R.id.rockeChangetET_ID);
        nagatET = findViewById(R.id.nagatChangeET_ID);

        roketCngBtn =findViewById(R.id.rocketChangBtnID);
        nagatCngBtn = findViewById(R.id.nagatChangeBtnID);



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textview_sampleID,fundType);
        spinner_fund.setAdapter(arrayAdapter);


        inboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,ChatActivity.class);
                intent.putExtra("key","1");
                startActivity(intent);

            }

        });

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactionId = transactionET.getText().toString().trim();

                FirebaseDatabase database =FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("User");
                reference.child("transactionId").child(pushId).child("id").setValue(transactionId)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        depositListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,DepositListActivity.class);
               // intent.putExtra("key","1");
                startActivity(intent);
            }
        });
        withdrawListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,WithdrawListActivity.class);
                startActivity(intent);
            }
        });


        publish_noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User").child("notice");
                String noticeTxt = noticeET.getText().toString();
                reference.setValue(noticeTxt);
                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();

            }
        });

        changeNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User").child("paymentNo");
                String phoneTxt = phoneET.getText().toString();
                reference.setValue(phoneTxt);
                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();
            }
        });
        roketCngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User").child("rocket");
                String phoneTxt = roketET.getText().toString();
                reference.setValue(phoneTxt);
                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();
            }
        });
        nagatCngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User").child("nagat");
                String phoneTxt = nagatET.getText().toString();
                reference.setValue(phoneTxt);
                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();
            }
        });

        fundChangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value;
                String fund_type = spinner_fund.getSelectedItem().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

                if (fund_type.equals("BDT")){
                    value="1";  //for BDT
                }
                else if (fund_type.equals("USD")){
                    value="2";  // for USD
                }
                else if (fund_type.equals("USD/BDT")){
                    value="3"; //for BDT and USD
                }
                else{
                    value ="4";
                }
                reference.child("available").setValue(value);
                Toast.makeText(AdminActivity.this, "Success !", Toast.LENGTH_SHORT).show();

            }
        });

        
    }


    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("status");

        reference.setValue("1");

    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("status");
        reference.setValue("2");
    }
}
