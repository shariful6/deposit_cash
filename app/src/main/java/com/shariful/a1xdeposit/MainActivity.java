package com.shariful.a1xdeposit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ClipboardManager clipboardManager;

    Button depositBtn,withdrawBtn,coppyBtn,rocketBtn,nagatBtn;
    TextView noticeTV,availableTV,adminNumberTv,statusTV;

    String aValue;

     String myUid;
     DatabaseReference ref;
     String mobile;

     String rocketNum;
     String nagatNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        myUid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        clipboardManager =(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        depositBtn = findViewById(R.id.depositBtnID);
        withdrawBtn = findViewById(R.id.withdrawtBtnID);
        noticeTV =findViewById(R.id.noticeTV_Id);
        availableTV = findViewById(R.id.availableTV_id);
        coppyBtn =findViewById(R.id.coppyBtnID);
        rocketBtn =findViewById(R.id.rocketBtnID);
        nagatBtn =findViewById(R.id.nagatBtnID);

        statusTV =findViewById(R.id.online_offlineTvID);


        coppyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ClipData clipData = ClipData.newPlainText("txt",mobile);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivity.this, "Copied !", Toast.LENGTH_SHORT).show();

            }
        });
        rocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData clipData = ClipData.newPlainText("txt",rocketNum);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Copied !", Toast.LENGTH_SHORT).show();

            }
        });
        nagatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData clipData = ClipData.newPlainText("txt",nagatNum);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Copied !", Toast.LENGTH_SHORT).show();

            }
        });

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DepositActivity.class);
                startActivity(intent);
            }
        });


        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WithdrawActivity.class);
                startActivity(intent);
            }
        });


        retriveNotice();
        retrivePaymentNo();
        retriveAvailablity();
        onlineStatus();
        retriveRocket();
        retriveNagat();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, ChatActivity.class);
               intent.putExtra("key","2");
               startActivity(intent);
            }
        });
    }

    private void retriveRocket() {

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User");
        reference.child("rocket").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    rocketNum = dataSnapshot.getValue(String.class);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void retriveNagat() {

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User");
        reference.child("nagat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nagatNum = dataSnapshot.getValue(String.class);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void onlineStatus() {
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("status");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String status = dataSnapshot.getValue(String.class);
                    if (status.equals("1"))
                    {
                        statusTV.setText("Admin Online");
                    }
                    else
                        {
                        statusTV.setText("Admin Offline");
                        }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void retriveNotice() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String rNotice = dataSnapshot.getValue(String.class);
                    noticeTV.setText(rNotice);
                    noticeTV.setSelected(true);
                }
                else
                {
                    noticeTV.setText("No notice available ...!!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void retrivePaymentNo() {

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User");
        reference.child("paymentNo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    mobile = dataSnapshot.getValue(String.class);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void retriveAvailablity() {

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("User");
        reference.child("available").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    aValue = dataSnapshot.getValue(String.class);

                    if (aValue.equals("1")){
                        availableTV.setText("Only BDT Available");
                    }
                    else if (aValue.equals("2")){
                        availableTV.setText("Only USD Available");
                    }
                    else if (aValue.equals("3")){
                        availableTV.setText("BDT/USD Available");
                    }
                    else{
                        availableTV.setText("No Fund Available !");
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


//--------------------Menu Section------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.admin_panelID) {
           openDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openDialog()
    {
        AdminDialog adminDialog=new AdminDialog();
        adminDialog.show(getSupportFragmentManager(),"Admin Login");

    }



}
