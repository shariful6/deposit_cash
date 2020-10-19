package com.shariful.a1xdeposit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class AdapterWithdraw extends RecyclerView.Adapter<AdapterWithdraw.MyViewHolder>{

    Context context;
    ArrayList<ModelWithdraw> withdrawList;

    public AdapterWithdraw(Context context, ArrayList<ModelWithdraw> withdrawList) {
        this.context = context;
        this.withdrawList = withdrawList;
    }

     @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_withdraw, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final String pId =withdrawList.get(position).getpId();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pId));
        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();
        //get data
        String userId =withdrawList.get(position).getUserId();
        String code =withdrawList.get(position).getCode();
        String ammount =withdrawList.get(position).getAmmount();
        String paymentMethod =withdrawList.get(position).getPayment_method();
        String currencyType =withdrawList.get(position).getCurrency_type();
        String phone =withdrawList.get(position).getPhone();
        String status =withdrawList.get(position).getStatus();

        //set data
        holder.userIdTV.setText("User Id: "+userId);
        holder.codeTv.setText("Code: "+code);
        holder.ammountTv.setText("Ammount: "+ammount);
        holder.paymentMethodTv.setText("Payment Method: "+paymentMethod);
        holder.currencyTypeTv.setText("Currency Type: "+currencyType);
        holder.phoneTv.setText("Phone: "+phone);
        holder.statusTv.setText(status);
        holder.dateTv.setText(pTime);


        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status","Approved");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Withdraw");
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


        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
        });



    }

    @Override
    public int getItemCount() {
        return withdrawList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userIdTV,codeTv,ammountTv,currencyTypeTv,paymentMethodTv,phoneTv,statusTv,dateTv;
        ImageButton deleteItemBtn;
        Button approveBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            userIdTV = itemView.findViewById(R.id.user_w);
            codeTv = itemView.findViewById(R.id.code_w);
            ammountTv = itemView.findViewById(R.id.amount_w);
            currencyTypeTv = itemView.findViewById(R.id.currencyType_w);
            paymentMethodTv = itemView.findViewById(R.id.paymentMethod_w);
            phoneTv = itemView.findViewById(R.id.phone_w);
            deleteItemBtn =itemView.findViewById(R.id.deleteBtnId_w);
            statusTv =itemView.findViewById(R.id.statusTVID);
            approveBtn =itemView.findViewById(R.id.okBtnID);
            dateTv =itemView.findViewById(R.id.date);


        }
    }
}
