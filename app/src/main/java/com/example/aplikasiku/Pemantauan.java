package com.example.aplikasiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

public class Pemantauan extends AppCompatActivity {
    ImageView Panah1, Panah2, Panah3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemantauan);

        Panah1 = (ImageView) findViewById(R.id.panah1);
        Panah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(Pemantauan.this, Rate.class);
                startActivity(I);
                finish();
            }
        });

        Panah2 = (ImageView) findViewById(R.id.panah2);
        Panah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(Pemantauan.this, Debit.class);
                startActivity(I);
                finish();
            }
        });

        Panah3 = (ImageView) findViewById(R.id.panah3);
        Panah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(Pemantauan.this, Kebocoran.class);
                startActivity(I);
                finish();
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("PEMANTAUAN");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Pemantauan.this, MainActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}