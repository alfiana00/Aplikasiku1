package com.example.aplikasiku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Debit extends AppCompatActivity  {

    Calendar inputTanggal1;
    Calendar inputTanggal2;
    DatePickerDialog.OnDateSetListener datestart;
    DatePickerDialog.OnDateSetListener dateend;

    EditText tanggal1, tanggal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);
        final Spinner List = findViewById(R.id.listgedung);

        tanggal1 = findViewById(R.id.tanggal1);
        tanggal2 = findViewById(R.id.tanggal2);

        inputTanggal1 = Calendar.getInstance();
        datestart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                inputTanggal1.set(Calendar.YEAR, year);
                inputTanggal1.set(Calendar.MONTH, monthOfYear);
                inputTanggal1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                EditText tanggalawal = findViewById(R.id.tanggal1);
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

                EditText tanggalakhir = findViewById(R.id.tanggal2);
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