package com.example.aplikasiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;

public class Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("KECEPATAN RATE AIR");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Rate.this, Pemantauan.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}