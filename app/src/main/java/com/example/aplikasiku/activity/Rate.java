package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.aplikasiku.R;

public class Rate extends AppCompatActivity {
    CardView cvA, cvB, cvC, cvD, cvP;
    ImageView btnHome, btnBack, btnGdp, btnGdA, btnGdB, btnGdC, btnGdD;
    TextView txtRateP,txtRateA, txtRateB,txtRateC,txtRateD;
    String namaGedung, gedungA, gedungB, gedungC, gedungD, gedungP;
    Handler mHandler;
    ProgressDialog pDialog;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("PEMANTAUAN REALTIME");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Pemantauan.class));
                finish();
            }
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cvA=findViewById(R.id.cv_ga);
        cvB=findViewById(R.id.cv_gb);
        cvC=findViewById(R.id.cv_gc);
        cvD = findViewById(R.id.cv_gd);
        cvP = findViewById(R.id.cv_gp);

        cvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rate.this, RateRealtimeChart.class);
                startActivity(intent);
            }
        });
        cvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rate.this, VolumeChart.class);
                startActivity(intent);
            }
        });

        txtRateP = findViewById(R.id.txt_rapusat);
        txtRateA = findViewById(R.id.txt_raa);
        txtRateB = findViewById(R.id.txt_rab);
        txtRateC = findViewById(R.id.txt_rac);
        txtRateD = findViewById(R.id.txt_rad);
        btnHome = findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Rate.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        gedungA = "rateA";
        gedungB = "rateB";
        gedungC = "rateC";
        gedungD = "rateD";
        gedungP = "rateP";
        swipeLayout = findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });

        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright));
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