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

        final Spinner List = findViewById(R.id.cv_gd);

        tanggal1 = findViewById(R.id.tglawal);
        tanggal2 = findViewById(R.id.tglakhir);
        btnBack = findViewById(R.id.btn_back);
        btnHome = findViewById(R.id.btn_home);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Debit.this, Pemantauan.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Debit.this, MainActivity.class);
                startActivity(i);
            }
        });

        inputTanggal1 = Calendar.getInstance();
        datestart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                inputTanggal1.set(Calendar.YEAR, year);
                inputTanggal1.set(Calendar.MONTH, monthOfYear);
                inputTanggal1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                EditText tanggalawal = findViewById(R.id.tglawal);
                String myFormat = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tanggalawal.setText(sdf.format(inputTanggal1.getTime()));
            }
        };
        inputTanggal2 = Calendar.getInstance();
        dateend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                inputTanggal2.set(Calendar.YEAR, year);
                inputTanggal2.set(Calendar.MONTH, monthOfYear);
                inputTanggal2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                EditText tanggalakhir = findViewById(R.id.tglakhir);
                String myFormat = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tanggalakhir.setText(sdf.format(inputTanggal2.getTime()));

            }
        };

        tanggal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Debit.this, datestart,
                        inputTanggal1.get(Calendar.YEAR),
                        inputTanggal1.get(Calendar.MONTH),
                        inputTanggal1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tanggal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Debit.this, dateend,
                        inputTanggal2.get(Calendar.YEAR),
                        inputTanggal2.get(Calendar.MONTH),
                        inputTanggal2.get(Calendar.DAY_OF_MONTH)).show();
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