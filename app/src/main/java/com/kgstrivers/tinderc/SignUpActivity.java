package com.kgstrivers.tinderc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.kgstrivers.tinderc.Model.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {


    private TextView alreadyuser;
    private FirebaseAuth mAuth;
    private Button register;
    private ImageButton  signupgoogle,signupfacebook;
    private EditText signupemail,signuppass,signupcpass,signupname;
    private RadioGroup radioGroup;
    private RadioButton male;
    private RadioButton female;
    private FirebaseAuth.AuthStateListener mAuthState;


    String femail,fpass,fname,fsex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        initialize();

        alreadyuser.setOnClickListener(v -> {
            Intent a = new Intent(SignUpActivity.this,SignInActivity.class);
            startActivity(a);
            finish();
            return;
        });

        mAuthState = firebaseAuth -> {
            final FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

            if(firebaseUser!=null)
            {
                Intent b = new Intent(SignUpActivity.this,MainPageActivity.class);
                startActivity(b);
                finish();
                return;
            }
        };

        register.setOnClickListener(v -> {
            final String email = signupemail.getText().toString();
            final String pass = signuppass.getText().toString();
            final String cpass =  signupcpass.getText().toString();
            final String name = signupname.getText().toString();
            String sex = "";

            if(male.isChecked())
            {
                sex = male.getText().toString();
            }
            else if(female.isChecked())
            {
                sex = female.getText().toString();
            }

            if(email.isEmpty())
            {
                signupemail.setError("Empty Email");
            }
            else if(pass.isEmpty())
            {
                signuppass.setError("Empty Password");
            }
            else if(cpass.isEmpty())
            {
                signupcpass.setError("Empty Confirm Password");
            }
            else if(!cpass.equals(pass))
            {
                signupcpass.setText("");
                signupcpass.setError("Not matched with Password");
            }
            else
            {
                if(name.isEmpty())
                {
                    signupname.setError("Name Empty");
                }
                else if(sex.isEmpty())
                {
                    Toast.makeText(this, "Sex is not Selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String finalSex = sex;

                    femail = email;
                    fpass = pass;
                    fname = name;
                    fsex = finalSex;

                    Intent  tr = new Intent(SignUpActivity.this,SignupImageActivity.class);
                    tr.putExtra("email",femail);
                    tr.putExtra("pass",fpass);
                    tr.putExtra("name",fname);
                    tr.putExtra("sex",fsex);

                    startActivity(tr);
                }

            }



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
        alreadyuser = findViewById(R.id.alreadyuser);

        register = findViewById(R.id.register);
        signupemail = findViewById(R.id.signupemail);
        signuppass = findViewById(R.id.signuppass);
        signupcpass = findViewById(R.id.signupconfirmpass);
        signupname = findViewById(R.id.signupname);
        radioGroup = findViewById(R.id.radiogroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

    }
}