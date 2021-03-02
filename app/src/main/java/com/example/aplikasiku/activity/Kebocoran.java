package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.aplikasiku.R;

public class Kebocoran extends AppCompatActivity {

    ImageView btnBack,btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebocoran);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("KEBOCORAN AIR");
        mToolbar.setTitleTextColor(Color.WHITE);

        btnBack = findViewById(R.id.btn_back);
        btnHome = findViewById(R.id.btn_home);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Kebocoran.this, Pemantauan.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Kebocoran.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Kebocoran.this, Pemantauan.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}