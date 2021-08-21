package com.kgstrivers.tinderc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private long backPressedtime;
    private Button SignupButton,signinbutton;
    private Window w;
    private FirebaseAuth mAuth;
    @Override
    public void onBackPressed() {//Double Back Pressed  and Exit Function

       bckpress();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        if(mAuth.getCurrentUser()!=null)
        {
            Intent c = new Intent(MainActivity.this,MainPageActivity.class);
            startActivity(c);
        }

        changestatusbarcolor();

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(a);
            }
        });
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(b);
            }
        });


    }



    private void initialize()
    {
        mAuth = FirebaseAuth.getInstance();
       SignupButton = findViewById(R.id.signupbutton);
       signinbutton = findViewById(R.id.signinbutton);
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

    private void changestatusbarcolor()
    {
        if(Build.VERSION.SDK_INT>=21) {
            w = this.getWindow();

            w.setStatusBarColor(this.getResources().getColor(R.color.teal_200));
        }
    }

}