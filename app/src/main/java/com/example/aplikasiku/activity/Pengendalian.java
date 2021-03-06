package com.example.aplikasiku.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.aplikasiku.R;
import com.example.aplikasiku.activity.MainActivity;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.RealtimeResponse;
import com.example.aplikasiku.model.StatusButtonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pengendalian extends AppCompatActivity {
    Switch swP, swA, swB, swC, swD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengendalian);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("PENGENDALIAN");
        mToolbar.setTitleTextColor(Color.WHITE);
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

        swA = findViewById(R.id.switchBtnA);
        swB = findViewById(R.id.switchBtnB);
        swC = findViewById(R.id.switchBtnC);
        swD = findViewById(R.id.switchBtnD);
        swP = findViewById(R.id.switchBtnP);

        swA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    private void getData()
    {

        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<StatusButtonResponse> callP = service.getStatusButton("5");
        callP.enqueue(new Callback<StatusButtonResponse>() {
            @Override
            public void onResponse(Call<StatusButtonResponse> call, Response<StatusButtonResponse> response) {
                if (response.body().isSuccess()){
                    String dtP = response.body().getData().getStatus();
                    if (dtP.equals("15")){
                        swP.setChecked(false);
                    }
                    else {
                        swP.setChecked(true);
                    }
                }
                else {
                    Toast.makeText(Pengendalian.this, "Gagal Mengambil Data!"+response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusButtonResponse> call, Throwable t) {

                Toast.makeText(Pengendalian.this, "Gagal Mengambil Data!" +t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        Call<StatusButtonResponse> callA = service.getStatusButton("1");
        callA.enqueue(new Callback<StatusButtonResponse>() {
            @Override
            public void onResponse(Call<StatusButtonResponse> call, Response<StatusButtonResponse> response) {
                if (response.body().isSuccess()){
                    String dtP = response.body().getData().getStatus();
                    if (dtP.equals("11")){
                        swA.setChecked(false);
                    }
                    else {
                        swA.setChecked(true);
                    }
                }
                else {
                    Toast.makeText(Pengendalian.this, "Gagal Mengambil Data!"+response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusButtonResponse> call, Throwable t) {

                Toast.makeText(Pengendalian.this, "Gagal Mengambil Data!" +t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        Call<StatusButtonResponse> callB = service.getStatusButton("2");
        callB.enqueue(new Callback<StatusButtonResponse>() {
            @Override
            public void onResponse(Call<StatusButtonResponse> call, Response<StatusButtonResponse> response) {
                if (response.body().isSuccess()){
                    if (response.body().getData().getStatus().equals("12")){
                        swB.setChecked(false);
                    }
                    else {
                        swC.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusButtonResponse> call, Throwable t) {

            }
        });
        Call<StatusButtonResponse> callC = service.getStatusButton("3");
        callC.enqueue(new Callback<StatusButtonResponse>() {
            @Override
            public void onResponse(Call<StatusButtonResponse> call, Response<StatusButtonResponse> response) {
                String dtC = response.body().getData().getStatus();
                if (dtC.equals("13")){
                    swC.setChecked(false);
                }
                else {
                    swC.setChecked(true);
                }
            }

            @Override
            public void onFailure(Call<StatusButtonResponse> call, Throwable t) {

            }
        });
        Call<StatusButtonResponse> callD = service.getStatusButton("4");
        callD.enqueue(new Callback<StatusButtonResponse>() {
            @Override
            public void onResponse(Call<StatusButtonResponse> call, Response<StatusButtonResponse> response) {
                String dtD = response.body().getData().getStatus();
                if (dtD.equals("14")){
                    swD.setChecked(false);
                }
                else
                {
                    swD.setChecked(true);
                }
            }

            @Override
            public void onFailure(Call<StatusButtonResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Pengendalian.this, MainActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}