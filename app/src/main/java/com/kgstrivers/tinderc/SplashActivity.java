package com.kgstrivers.tinderc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };
        thread.start();
    }
}