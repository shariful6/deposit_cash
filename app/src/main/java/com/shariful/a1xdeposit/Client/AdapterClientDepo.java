package com.shariful.a1xdeposit.Client;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shariful.a1xdeposit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterClientDepo extends RecyclerView.Adapter<AdapterClientDepo.MyViewHolder> {

    Context context;

    ArrayList<ClientModelDeposit> depositList2;

    public AdapterClientDepo(Context context, ArrayList<ClientModelDeposit> depositList2) {

        this.context = context;
        this.depositList2 = depositList2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final String pId =depositList2.get(position).getpId();
        final String uid= depositList2.get(position).getMyUid();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pId));
        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        holder.userIdTV.setText("User Id: "+depositList2.get(position).getUserId());
        holder.amountTv.setText("Ammount: "+depositList2.get(position).getAmount());
        holder.currencyTypeTv.setText("Currency Type: "+depositList2.get(position).getCurrency_type());
        holder.paymentMethodTv.setText("Payment Method: "+depositList2.get(position).getPayment_method());
        holder.phoneTv.setText("Phone: "+depositList2.get(position).getPhone());
        holder.transactionIdTV.setText("Transaction Id: "+depositList2.get(position).getTransactionId());
        holder.statusTv.setText(""+depositList2.get(position).getStatus());
        holder.dateTV.setText(pTime);



        holder.moreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (myUid.equals(uid)){

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
                else{
                    Toast.makeText(context, "You Can not delete other request !!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return depositList2.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userIdTV,amountTv,currencyTypeTv,paymentMethodTv,phoneTv,transactionIdTV,statusTv,dateTV;
        ImageButton moreBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            userIdTV = itemView.findViewById(R.id.userIdTV_ID2);
            amountTv = itemView.findViewById(R.id.amountTvID2);
            currencyTypeTv = itemView.findViewById(R.id.currencyTypeTVID2);
            paymentMethodTv = itemView.findViewById(R.id.paymentMethodTvID2);
            phoneTv = itemView.findViewById(R.id.phoneTvID2);
            transactionIdTV = itemView.findViewById(R.id.transactionTvID2);
            statusTv =itemView.findViewById(R.id.statusTVID2);
            moreBtn = itemView.findViewById(R.id.moreBtnID2);
            dateTV = itemView.findViewById(R.id.dateTvID2);

        }
    }

}
