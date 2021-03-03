package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.aplikasiku.R;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRealtime;
import com.example.aplikasiku.model.RealtimeResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rate extends AppCompatActivity {
    ImageView btnHome, btnBack, btnGdp, btnGdA, btnGdB, btnGdC, btnGdD;
    TextView txtRateP,txtRateA, txtRateB,txtRateC,txtRateD;
    String namaGedung, gedungA, gedungB, gedungC, gedungD, gedungP;
    Handler mHandler;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

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

        btnGdA = findViewById(R.id.panaha);
        btnGdB = findViewById(R.id.panahb);
        btnGdC = findViewById(R.id.panahc);
        btnGdD = findViewById(R.id.panahd);
        btnGdp = findViewById(R.id.panah1);
        txtRateP = findViewById(R.id.txt_gdpusat);
        txtRateA = findViewById(R.id.txt_gda);
        txtRateB = findViewById(R.id.txt_gdb);
        txtRateC = findViewById(R.id.txt_gdc);
        txtRateD = findViewById(R.id.txt_gdd);

        btnHome = findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Rate.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
//        this.mHandler = new Handler();
//        this.mHandler.postDelayed(m_Runnable,5000);
        gedungA = "rateA";
        gedungB = "rateB";
        gedungC = "rateC";
        gedungD = "rateD";
        gedungP = "rateP";
        pDialog = new ProgressDialog(Rate.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<RealtimeResponse> call = service.getRealtime(gedungA);
        call.enqueue(new Callback<RealtimeResponse>() {
            @Override
            public void onResponse(Call<RealtimeResponse> call, Response<RealtimeResponse> response) {
                if(response.body().isSuccess()){
                    JsonParser parser = new JsonParser();
                    final JsonObject jsonObject = (JsonObject) parser.parse(response.body().getData().getRate());
                    txtRateA.setText(jsonObject.get(gedungA).getAsString());
                }
                else {
                    String resp = response.body().getMessage().toString();
                    Log.d("fff", "resp : "+resp);
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RealtimeResponse> call, Throwable t) {

                String resp = t.getMessage();
                Log.d("fff", "resp : "+resp);
                pDialog.dismiss();
            }
        });

    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(Rate.this,"Data Diperbarui",Toast.LENGTH_SHORT).show();

            Rate.this.mHandler.postDelayed(m_Runnable, 5000);
        }

    };
    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
        finish();

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