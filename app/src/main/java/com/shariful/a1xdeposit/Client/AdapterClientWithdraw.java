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


public class AdapterClientWithdraw extends RecyclerView.Adapter<AdapterClientWithdraw.MyViewHolder> {

    Context context;
    ArrayList<ClientModelWithdraw> withdrawList2;

    public AdapterClientWithdraw(Context context, ArrayList<ClientModelWithdraw> withdrawList2) {

        this.context = context;
        this.withdrawList2 = withdrawList2;
    }

    @Override
    public AdapterClientWithdraw.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_withdraw2, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterClientWithdraw.MyViewHolder holder, int position) {

        final String pId =withdrawList2.get(position).getpId();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pId));
        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        final String uid= withdrawList2.get(position).getMyUid();
        //get data
        String userId =withdrawList2.get(position).getUserId();
        String code =withdrawList2.get(position).getCode();
        String ammount =withdrawList2.get(position).getAmmount();
        String paymentMethod =withdrawList2.get(position).getPayment_method();
        String currencyType =withdrawList2.get(position).getCurrency_type();
        String phone =withdrawList2.get(position).getPhone();
        String status =withdrawList2.get(position).getStatus();

        //set data
        holder.userIdTV.setText("User Id: "+userId);
        holder.codeTv.setText("Code: "+code);
        holder.ammountTv.setText("Ammount: "+ammount);
        holder.paymentMethodTv.setText("Payment Method: "+paymentMethod);
        holder.currencyTypeTv.setText("Currency Type: "+currencyType);
        holder.phoneTv.setText("Phone: "+phone);
        holder.statusTv.setText(status);
        holder.dateTv.setText(pTime);


        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (myUid.equals(uid)){

                    Query fquery = FirebaseDatabase.getInstance().getReference("Withdraw").orderByChild("pId").equalTo(pId);
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

                    Toast.makeText(context, "You can not delete other request !!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return withdrawList2.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userIdTV,codeTv,ammountTv,currencyTypeTv,paymentMethodTv,phoneTv,statusTv,dateTv;
        ImageButton deleteItemBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            userIdTV = itemView.findViewById(R.id.user_w2);
            codeTv = itemView.findViewById(R.id.code_w2);
            ammountTv = itemView.findViewById(R.id.amount_w2);
            currencyTypeTv = itemView.findViewById(R.id.currencyType_w2);
            paymentMethodTv = itemView.findViewById(R.id.paymentMethod_w2);
            phoneTv = itemView.findViewById(R.id.phone_w2);
            deleteItemBtn =itemView.findViewById(R.id.deleteBtnId_w2);
            statusTv = itemView.findViewById(R.id.statusTVID);
            dateTv = itemView.findViewById(R.id.date2);

        }
    }
}
