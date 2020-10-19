package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shariful.a1xdeposit.Client.ClientWithListActivity;

import java.util.HashMap;

public class WithdrawActivity extends AppCompatActivity {

    String[] payment_method1;
    String[] currency_method1;

    Button withdrawSubmitBtn;
    EditText userIdET,codeET,ammountET,phoneET;

    Spinner currency_typeSP,paymentMethodSP;

    String pushId;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        this.setTitle("Withdraw Here");

        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
        pushId = myRef.push().getKey();

        payment_method1 =getResources().getStringArray(R.array.payment_method);
        currency_method1 =getResources().getStringArray(R.array.currency);

        withdrawSubmitBtn= findViewById(R.id.WithdrawSubmitBtnID);
        userIdET  = findViewById(R.id.userId_w);
        codeET =findViewById(R.id.codeID_w);
        ammountET = findViewById(R.id.amountID_w);
        phoneET = findViewById(R.id.phoneID_w);

        currency_typeSP = findViewById(R.id.spinner_currencyID_w);
        paymentMethodSP = findViewById(R.id.spinner_ID_w);


       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textview_sampleID,payment_method1);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textview_sampleID,currency_method1);
       paymentMethodSP.setAdapter(arrayAdapter);
       currency_typeSP.setAdapter(adapter);


        withdrawSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitWithdraw();
            }
        });



    }

    private void submitWithdraw() {

        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "Deposit/" + "deposit_" + timeStamp;

        String userId = userIdET.getText().toString();
        String code = codeET.getText().toString();
        String amount = ammountET.getText().toString();
        String phone = phoneET.getText().toString();

        String currencyType = currency_typeSP.getSelectedItem().toString();
        String paymentMethod = paymentMethodSP.getSelectedItem().toString();



        if (TextUtils.isEmpty(userId)){
            userIdET.setError("Enter User Id");
            userIdET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(amount)){
            ammountET.setError("Enter Amount");
            ammountET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)){
            phoneET.setError("Enter Phone Number");
            phoneET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(code)){
            codeET.setError("Enter Transaction Id");
            codeET.requestFocus();
            return;
        }

        else {

            HashMap<Object,String> hashMap = new HashMap<>();
            hashMap.put("userId",userId);
            hashMap.put("code",code);
            hashMap.put("ammount",amount);
            hashMap.put("payment_method",paymentMethod);
            hashMap.put("currency_type",currencyType);
            hashMap.put("phone",phone);
            hashMap.put("pId",timeStamp);
            hashMap.put("myUid",myUid);
            hashMap.put("status","Pending");

            FirebaseDatabase database =FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Withdraw");
            //put data within hasmap in database
            reference.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(WithdrawActivity.this, "Withdraw Request Submitted !!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WithdrawActivity.this, ClientWithListActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(WithdrawActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

             userIdET.setText("");
             codeET.setText("");
             ammountET.setText("");
             phoneET.setText("");


        }




    }


}
