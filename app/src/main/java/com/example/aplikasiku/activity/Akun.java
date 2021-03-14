package com.example.aplikasiku.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.aplikasiku.R;

public class Akun extends AppCompatActivity {
    Button btn_logout;
    TextView txt_id, txt_user, txt_nama;
    String ID, username, nama;
    SharedPreferences sharedPreferences;
    boolean sudahLogin;
    CardView cvPassword;

    public static final String TAG_ID = "ID";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NAMA = "nama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        cvPassword = findViewById(R.id.ubahpswd);
        cvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(Akun.this, UbahPswd.class);
                startActivity(I);
                finish();
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("AKUN");
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


        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_user = (TextView) findViewById(R.id.txt_username);
        txt_nama = (TextView) findViewById(R.id.txt_nama);

        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);

        ID = sharedPreferences.getString("id", null);
        username = sharedPreferences.getString("username", null);
        nama = sharedPreferences.getString("nama", null);

        txt_id.setText(ID);
        txt_user.setText(username);
        txt_nama.setText(nama);

        Intent in = getIntent();
        String string = in.getStringExtra("message");
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Akun.this);
                builder.setMessage("Apakah Anda yakin untuk keluar?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("sudahLogin", false);
                                editor.putString("nama", null);
                                editor.putString("username", null);
                                editor.apply();

                                //Starting login activity
                                Intent intent = new Intent(Akun.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
        Intent i = new Intent(Akun.this, MainActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}
