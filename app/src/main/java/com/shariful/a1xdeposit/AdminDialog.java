package com.shariful.a1xdeposit;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDialog extends AppCompatDialogFragment {

    EditText pinNumber;
    String adminPin;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        retriveAdminPin();


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pin=pinNumber.getText().toString();
                        if(pin.equals(adminPin))
                        {
                            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),AdminActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getActivity(), "Wrong Pin...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        pinNumber=view.findViewById(R.id.pinID);


        return  builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    private void retriveAdminPin() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminPin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adminPin =dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public interface AdminDialogListener{

        void applyTexts(String pinNumber);

    }


}
