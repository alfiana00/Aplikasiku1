package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.aplikasiku.R;
import com.example.aplikasiku.adapter.RecyclerDataAdapter;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRate;
import com.example.aplikasiku.model.RateResponse;
import com.example.aplikasiku.model.RealtimeResponse;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateDataFormat;

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
    Button btnPilih;
    CardView spnGedung, inputTanggal1, inputTanggal2;
    TextView tvTgl1, tvTgl2, titleRate;
    TextView tvNull;
    Spinner listGedung;
    private String[] judul = {"Rate Air (m³/s)"};
    LayoutInflater layoutInflater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    List<DataRate> dataList;
    private ArrayList<Float> listRate;
    private ArrayList<Long> listWaktu;
    private GridLayoutManager gridLayoutManager;
    private RecyclerDataAdapter recyclerDataAdapter;
    ProgressDialog progressDialog;



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
        btnPilih = findViewById(R.id.btn_pilih);
        listGedung = findViewById(R.id.lst_gedung);
        final TextView tvGedung = findViewById(R.id.pilihangedung);
        tvNull = findViewById(R.id.tvNull);
        titleRate = findViewById(R.id.title_rate);
        Date c = Calendar.getInstance().getTime();


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Debit.this, MainActivity.class);
                startActivity(i);
            }
        });

        ButterKnife.bind(this);
        mCalendar = Calendar.getInstance();
        String tglIni = DateDataFormat.format(c).toString();
        Log.i("tgl", "sekarang tanggal : "+tglIni);
        tvGedung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnGedung.isShown();
                tvGedung.setVisibility(View.GONE);
            }
        });

        showTable("rateP", tglIni, tglIni);
        cvTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Debit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        tvTglAwal.setText(DateDataFormat.format(mCalendar.getTime()));
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

                        tvTglAkhir.setText(DateDataFormat.format(mCalendar.getTime()));
                    }
                },
                        mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String waktu1 = tvTglAwal.getText().toString();
                String waktu2 = tvTglAkhir.getText().toString();
                String gedung = listGedung.getSelectedItem().toString();
                if (gedung.equals("Pusat")){
                    showTable("rateP", waktu1, waktu2);
                    titleRate.setText("Rate Air (Gedung Pusat)");
                }
                else if (gedung.equals("Gedung A")){
                    showTable("rateA", waktu1, waktu2);
                    titleRate.setText("Rate Air (Gedung A)");

                }
                else if (gedung.equals("Gedung B")){
                    showTable("rateB", waktu1, waktu2);
                    titleRate.setText("Rate Air (Gedung B)");

                }
                else if (gedung.equals("Gedung C")){
                    showTable("rateC", waktu1, waktu2);
                    titleRate.setText("Rate Air (Gedung C)");

                }
                else if (gedung.equals("Gedung D")){
                    showTable("rateD", waktu1, waktu2);
                    titleRate.setText("Rate Air (Gedung D)");

                }
                else {
                    Toast.makeText(Debit.this, "Silakan pilih gedung terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
                Log.i("aaaa", gedung+":"+waktu1 + "/" + waktu2);
            }
        });

    }

    public void showTable(String gedung, String waktu1, String waktu2){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<RateResponse> call = service.getRateAir(gedung, waktu1, waktu2);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {

                progressDialog = new ProgressDialog(Debit.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Memuat Data ...");
                progressDialog.show();
                if (response.body().isSuccess()){
                    if (response.body().getData() != null) {
                        dataList = response.body().getData();
                        recyclerView = findViewById(R.id.rv_datarate);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Debit.this));
                        RecyclerDataAdapter adapter = new RecyclerDataAdapter(dataList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        tvNull.setVisibility(View.GONE);
                    }
                    else {
                        recyclerView = findViewById(R.id.rv_datarate);
                        tvNull.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {

                recyclerView = findViewById(R.id.rv_datarate);
                tvNull.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
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