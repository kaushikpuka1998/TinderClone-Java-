package com.kgstrivers.tinderc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kgstrivers.tinderc.Model.Logind;
import com.kgstrivers.tinderc.Model.Logoutd;
import com.kgstrivers.tinderc.Model.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignInActivity extends AppCompatActivity {


    private TextView newuser;
    private Button signinbutton;
    private FirebaseAuth mAuth;
    private EditText signinemail,signinpass;
    private FirebaseAuth.AuthStateListener mAuthState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mAuth = FirebaseAuth.getInstance();
        initialize();
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(a);
                finish();
                return;
            }
        });

        mAuthState = firebaseAuth -> {
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            if(firebaseUser!=null)
            {
                Intent b = new Intent(SignInActivity.this,MainPageActivity.class);
                startActivity(b);
                finish();
                return;
            }
        };

        signinbutton.setOnClickListener(v -> {
            final String email = signinemail.getText().toString();
            final String pass = signinpass.getText().toString();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(SignInActivity.this, "Sign In Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String userid = mAuth.getCurrentUser().getUid();
                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm::ss a");
                            String date = df.format(Calendar.getInstance().getTime());

                            Logind userl = new Logind(mAuth.getCurrentUser().getEmail(),date);


                            DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Login");

                            dataref.setValue(userl);
                            Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthState);
    }

    private void initialize()
    {
        signinbutton = findViewById(R.id.SignInButtonSignInPage);
        signinemail = findViewById(R.id.signinemail);
        signinpass = findViewById(R.id.signinpass);
        newuser = findViewById(R.id.newuser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mAuth!=null)
        {

        }
    }
}