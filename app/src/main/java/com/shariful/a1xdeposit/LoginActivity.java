package com.shariful.a1xdeposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email_signIn,password_signIn;
    Button SignInBtn;
    TextView gotoSignUp;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("SignIn");

        mAuth = FirebaseAuth.getInstance();

        email_signIn=findViewById(R.id.emailID_signIn);
        password_signIn=findViewById(R.id.passID_signIn);
        SignInBtn=findViewById(R.id.singInBtnID);
        gotoSignUp=findViewById(R.id.tv_go_to_signUpId);
        progressBar=findViewById(R.id.progressbarID);


        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);

            }
        });


    }


    private void userLogin() {
        final String email = email_signIn.getText().toString().trim();
        String password = password_signIn.getText().toString().trim();

        if (email.isEmpty()) {
            email_signIn.setError("Enter an email address");
            email_signIn.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_signIn.setError("Enter a valid email address");
            email_signIn.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            password_signIn.setError("Enter a password");
            password_signIn.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User(LoginActivity.this);
                            user.setEmail(email);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            intent.putExtra("email", email);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Successfully Login !", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Failed To Login !!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



}