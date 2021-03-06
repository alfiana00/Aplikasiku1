package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.aplikasiku.R;
import com.example.aplikasiku.adapter.RecyclerDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Debit extends AppCompatActivity  {
    @BindView(R.id.tglawal)
            CardView cvTglAwal;
            @BindView(R.id.tvtglawal)
                    TextView tvTglAwal;
    @BindView(R.id.tglakhir)
    CardView cvTglAkhir;
    @BindView(R.id.tvtglakhir)
    TextView tvTglAkhir;
    Calendar mCalendar;
    ImageView btnHome;
    CardView spnGedung, inputTanggal1, inputTanggal2;
    TextView tvTgl1, tvTgl2;
    private String[] judul = {"Rate Air (m³/s)"};
    LayoutInflater layoutInflater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private ArrayList<Float> listRate;
    private ArrayList<Long> listWaktu;
    private RecyclerDataAdapter recyclerDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("KECEPATAN RATE AIR");
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

        spnGedung =findViewById(R.id.spn_gedung);
        inputTanggal1 = findViewById(R.id.tglawal);
        inputTanggal2 = findViewById(R.id.tglakhir);
        tvTgl1 = findViewById(R.id.tvtglawal);
        tvTgl2 = findViewById(R.id.tvtglakhir);
        btnHome = findViewById(R.id.btn_home);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Debit.this, MainActivity.class);
                startActivity(i);
            }
        });
        final Spinner ListGedung = findViewById(R.id.lst_gedung);

        ButterKnife.bind(this);
        mCalendar = Calendar.getInstance();


        cvTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Debit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTgl = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTgl);
                        tvTglAwal.setText(sdf.format(mCalendar.getTime()));
                    }
                },
                        mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        cvTglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Debit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTgl = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTgl);
                        tvTglAkhir.setText(sdf.format(mCalendar.getTime()));
                    }
                },
                        mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
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