package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;

import com.example.aplikasiku.MyMarkerView;
import com.example.aplikasiku.R;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.perwaktu.PerwaktuResponse;
import com.example.aplikasiku.model.perwaktu.RateItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateDataFormat;

public class RatePerwaktuChart extends AppCompatActivity {
    BarChart barChart;
    float yvalue;
    String gedung, waktu1, waktu2, tglIni, nama, kolom, tabel;
    Calendar mCalendar;
    public ArrayList<String> listRate;
    public ArrayList<String> listWaktu;
    public ArrayList<String> lisXTime;
    List<RateItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_perwaktu_chart);
        barChart = findViewById(R.id.chart);
        barChart.setFitBars(true);
        barChart.setNoDataText("Data Grafik Tidak Tersedia.");
        barChart.setNoDataTextColor(Color.rgb(3, 169, 244));
        Bundle bundle = getIntent().getExtras();
        tabel = bundle.getString("table");
        kolom = bundle.getString("column");
        waktu1 = bundle.getString("waktu1");
        waktu2 = bundle.getString("waktu2");
        Log.i("hui", tabel + kolom + waktu1 + waktu2);
        Date c = Calendar.getInstance().getTime();
        mCalendar = Calendar.getInstance();
        tglIni = DateDataFormat.format(c).toString();
        getDataBar(kolom, tabel, waktu1, waktu2);
    }

    public void getDataBar(String tabel, String kolom, String waktu1, String waktu2) {
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<PerwaktuResponse> call = service.getRatebyDate(tabel, kolom, waktu1, waktu2);
        ArrayList<BarEntry> DataVals = new ArrayList<>();
        call.enqueue(new Callback<PerwaktuResponse>() {
            @Override
            public void onResponse(Call<PerwaktuResponse> call, Response<PerwaktuResponse> response) {
                listRate = new ArrayList<>();
                listWaktu = new ArrayList<>();
                lisXTime = new ArrayList<>();

                if (response.body().isSuccess()) {
                    if (response.body().getData() != null) {
                        dataList = (List<RateItem>) response.body().getData().getRate();
                        int iterate = 0;
                        for (int i = 0; i < dataList.size(); i++) {
//                            Log.d("TAG", "onResponse: " + dataList.get(i).getWaktu());
                            RateItem x = dataList.get(i);
                            Float air = Float.parseFloat((x.getRate()).replace(",", ""));
                            lisXTime.add(dataList.get(i).getWaktu());
                            for (int j = 0; j < dataList.size(); j++) {
                                listRate.add(dataList.get(j).getRate());
                                listWaktu.add(dataList.get(j).getWaktu());
                            }
                            DataVals.add(new BarEntry(iterate, air));
                            iterate++;
                        }
                        ShowChartBar(DataVals, lisXTime);
                    }
                }
//                ShowChartBar(DataVals, lisXTime);
            }

            @Override
            public void onFailure(Call<PerwaktuResponse> call, Throwable t) {

            }
        });
    }

    private void ShowChartBar(ArrayList<BarEntry> DataVals, ArrayList<String> listTime) {
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.my_marker_view);
        barChart.setMarkerView(mv);
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
        String label = null;
        if (kolom.equals("rateP")) {
            label = "Volume Gedung Pusat";
        } else if (kolom.equals("rateA")) {
            label = "Volume Gedung A";
        } else if (kolom.equals("rateB")) {
            label = "Volume Gedung B";
        } else if (kolom.equals("rateC")) {
            label = "Volume Gedung C";
        } else if (kolom.equals("rateD")) {
            label = "Volume Gedung D";
        }
        BarDataSet barDataSet = new BarDataSet(DataVals, label);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barData.setHighlightEnabled(true);
        barData.setDrawValues(false);
        barDataSet.setValues(DataVals);
        barDataSet.getEntryCount();
        barDataSet.setBarBorderWidth(1f);
        barDataSet.setBarBorderColor(Color.rgb(3, 169, 244));
        barDataSet.setVisible(true);
        barDataSet.setDrawIcons(false);
        barDataSet.setValueTextSize(9f);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{
                10f, 5f
        }, 0f));
        barDataSet.setFormSize(10f);
        barDataSet.setColor(Color.rgb(3, 169, 244));
        YAxis leftaxisy = barChart.getAxisLeft();
        leftaxisy.removeAllLimitLines();
        //        leftaxisy.setAxisMaximum(100f);
        //        leftaxisy.setAxisMinimum(0f);
        leftaxisy.enableGridDashedLine(10f, 10f, 0f);
        leftaxisy.setDrawZeroLine(true);
        leftaxisy.setDrawLimitLinesBehindData(true);
        leftaxisy.setLabelCount(10, true);
        leftaxisy.setDrawGridLines(true);
        leftaxisy.setCenterAxisLabels(true);
        leftaxisy.setSpaceBottom(0);
//        leftaxisy.setXOffset(3f);
        leftaxisy.setDrawTopYLabelEntry(true);
//        leftaxisy.setSpaceMax(3f);
        leftaxisy.isForceLabelsEnabled();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.enableGridDashedLine(1f, 1f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularityEnabled(true);
//        xAxis.setLabelCount(10, false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return listTime.get((int) value);
            }
        });
        iBarDataSets.clear();
        iBarDataSets.add(barDataSet);
        barChart.clear();
        barChart.setData(barData);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(2000, Easing.EaseInOutBounce);
        barChart.invalidate();
    }
}