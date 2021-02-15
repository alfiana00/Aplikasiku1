package com.example.aplikasiku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class Akun extends AppCompatActivity {
    Button btn_logout;
    TextView txt_id, txt_user, txt_nama;
    String ID, username, nama;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "ID";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NAMA = "nama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_nama = (TextView) findViewById(R.id.txt_nama);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        ID = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        nama = getIntent().getStringExtra(TAG_NAMA);

        txt_id.setText("ID : " + ID);
        txt_user.setText("USERNAME :" + username);
        txt_nama.setText("NAMA :" + nama);

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

                                //Getting out sharedpreferences
                                sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(Login.session_status, false);
                                //Saving the sharedpreferences
                                editor.commit();

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
    public void onBackPressed()
    {
        Intent i = new Intent(Akun.this, MainActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}
