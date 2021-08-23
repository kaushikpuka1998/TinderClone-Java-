package com.kgstrivers.tinderc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kgstrivers.tinderc.Model.Logoutd;
import com.kgstrivers.tinderc.Model.Users;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainPageActivity extends AppCompatActivity {


    private TextView email;
    private Button logout;
    private FirebaseAuth mAuth;
    private long backPressedtime;
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    SwipeFlingAdapterView filingContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mAuth = FirebaseAuth.getInstance();

        initialize();

        al = new ArrayList<String>();
        al.add("php");
        al.add("c");
        al.add("python");
        al.add("java");

        //choose your favorite adapter
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.helloText, al);


        filingContainer.setAdapter(arrayAdapter);
        filingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {

                Toast.makeText(MainPageActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {

                Toast.makeText(MainPageActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int arri) {

                al.add("XML".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("Not:","Notified");
                i++;
            }

            @Override
            public void onScroll(float v) {

            }
        });

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
        filingContainer = (SwipeFlingAdapterView) findViewById(R.id.swipeadapter);

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