package com.example.aplikasiku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiku.activity.Login;

public class SplashScreen extends AppCompatActivity {

    private int splashtime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent I = new Intent(SplashScreen.this, Login.class);
                startActivity(I);
                finish();
            }
        }, splashtime);
    }
}