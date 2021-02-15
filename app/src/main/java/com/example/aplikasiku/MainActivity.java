package com.example.aplikasiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


public class MainActivity extends AppCompatActivity {
    ImageView akun, notifikasi;

    CardView Controlling, Monitoring, About , Help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    akun = (ImageView) findViewById(R.id.Akun);
    akun.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent I = new Intent(MainActivity.this, Akun.class);
            startActivity(I);
            finish();
        }
    });
    notifikasi = (ImageView) findViewById(R.id.Notifikasi);
    notifikasi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent I = new Intent(MainActivity.this, Notifikasi.class);
            startActivity(I);
            finish();
        }
    });

        Controlling = findViewById(R.id.Pengendalian);
        Controlling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, Pengendalian.class);
                startActivity(I);
                finish();
            }
        });

        Monitoring = findViewById(R.id.Pemantauan);
        Monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, Pemantauan.class);
                startActivity(I);
                finish();
            }
        });

        About = findViewById(R.id.Tentang);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, Tentang.class);
                startActivity(I);
                finish();
            }
        });

    }
}