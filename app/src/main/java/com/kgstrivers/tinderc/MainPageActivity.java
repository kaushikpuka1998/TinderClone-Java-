package com.kgstrivers.tinderc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kgstrivers.tinderc.Model.Logoutd;
import com.kgstrivers.tinderc.Model.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainPageActivity extends AppCompatActivity {


    private TextView email;
    private Button logout;
    private FirebaseAuth mAuth;
    private long backPressedtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mAuth = FirebaseAuth.getInstance();

        initialize();

        if(mAuth!=null)
        {
            String em = mAuth.getCurrentUser().getEmail().toString();
            email.setText(em);
        }





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(MainPageActivity.this,SignInActivity.class);

                String userid = mAuth.getCurrentUser().getUid();
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm::ss a");
                String date = df.format(Calendar.getInstance().getTime());

                Logoutd userl = new Logoutd(mAuth.getCurrentUser().getEmail(),date);


                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Logout");

                dataref.setValue(userl);
                mAuth.signOut();
                email.setText("");
                Toast.makeText(MainPageActivity.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                startActivity(b);
                finish();
            }
        });



    }


    private void initialize()
    {
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout);

    }

    private void bckpress()
    {
        if(backPressedtime + 2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            Intent in = new Intent(Intent.ACTION_MAIN);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in.addCategory(Intent.CATEGORY_HOME);
            startActivity(in);
            finish();
            System.exit(0);
        }else
        {
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedtime = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        if(mAuth!=null)
        {
            bckpress();
        }

    }
}