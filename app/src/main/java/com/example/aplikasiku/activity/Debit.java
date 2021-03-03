package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.aplikasiku.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Debit extends AppCompatActivity  {

    ImageView btnHome, btnBack;

    Calendar inputTanggal1;
    Calendar inputTanggal2;
    DatePickerDialog.OnDateSetListener datestart;
    DatePickerDialog.OnDateSetListener dateend;

    EditText tanggal1, tanggal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("DEBIT AIR");
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

        btnHome = findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Debit.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Debit.this, Pemantauan.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}