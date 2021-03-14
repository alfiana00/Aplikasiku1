package com.example.aplikasiku.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiku.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ActivityShowChart extends AppCompatActivity {
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float yvalue;
    String extra;
    LimitLine upper, lower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chart);

        Bundle bundle = getIntent().getExtras();
        extra = bundle.getString("nama");
        lineChart = findViewById(R.id.chart);
        lineChart.setNoDataText("Grafik Tidak Tersedia.");
        lineChart.setNoDataTextColor(Color.rgb(3,169,244));

    }
}