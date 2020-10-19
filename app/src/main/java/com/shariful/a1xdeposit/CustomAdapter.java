package com.shariful.a1xdeposit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    Context context;

    ArrayList<ModelDeposit> depositList;


    public CustomAdapter(Context context, ArrayList<ModelDeposit> depositList) {
        this.context = context;
        this.depositList = depositList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String pId =depositList.get(position).getpId();
        final String uid= depositList.get(position).getMyUid();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pId));
        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();


        holder.userIdTV.setText("User Id: "+depositList.get(position).getUserId());
        holder.amountTv.setText("Ammount: "+depositList.get(position).getAmount());
        holder.currencyTypeTv.setText("Currency Type: "+depositList.get(position).getCurrency_type());
        holder.paymentMethodTv.setText("Payment Method: "+depositList.get(position).getPayment_method());
        holder.phoneTv.setText("Phone: "+depositList.get(position).getPhone());
        holder.transactionIdTV.setText("Transaction Id: "+depositList.get(position).getTransactionId());
        holder.statusTv.setText(""+depositList.get(position).getStatus());
        holder.dateTV.setText(pTime);





        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status","Approved");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Deposit");
                //put data in this reference
                reference.child(pId).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(context, "Failed to Approved !!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });



        holder.moreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    // showPopup(holder.moreBtn,uid,myUid,pId,pImage); //for alternative deleteing method
                    // showMoreOption(holder.moreBtn);
                    Query fquery = FirebaseDatabase.getInstance().getReference("Deposit").orderByChild("pId").equalTo(pId);
                    fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()){

                                ds.getRef().removeValue();// remove value from firebase where pId matched

                            }

                            Toast.makeText(context, "Deleted !", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


            }
        });


    }


    @Override
    public int getItemCount() {
        return depositList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userIdTV,amountTv,currencyTypeTv,paymentMethodTv,phoneTv,transactionIdTV,statusTv,dateTV;
        ImageButton moreBtn;
        Button approveBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            approveBtn =itemView.findViewById(R.id.okBtnID);
            userIdTV = itemView.findViewById(R.id.userIdTV_ID);
            amountTv = itemView.findViewById(R.id.amountTvID);
            currencyTypeTv = itemView.findViewById(R.id.currencyTypeTVID);
            paymentMethodTv = itemView.findViewById(R.id.paymentMethodTvID);
            phoneTv = itemView.findViewById(R.id.phoneTvID);
            transactionIdTV = itemView.findViewById(R.id.transactionTvID);
            statusTv =itemView.findViewById(R.id.statusTVID);
            moreBtn = itemView.findViewById(R.id.moreBtnID);
            dateTV = itemView.findViewById(R.id.dateTvID);

        }
    }
}
