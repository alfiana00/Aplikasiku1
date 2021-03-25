package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aplikasiku.MyMarkerView;
import com.example.aplikasiku.R;
import com.example.aplikasiku.adapter.RecyclerDataAdapter;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRate;
import com.example.aplikasiku.model.DataRateRealtime;
import com.example.aplikasiku.model.DataVolumeRatePerwaktu;
import com.example.aplikasiku.model.RateItem;
import com.example.aplikasiku.model.RateRealtimeResponse;
import com.example.aplikasiku.model.RateResponse;
import com.example.aplikasiku.model.VolumePerwaktuItem;
import com.example.aplikasiku.model.VolumePerwaktuResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateDataFormat;
import static com.example.aplikasiku.apiinterface.DataInterface.DateFormat;
import static com.example.aplikasiku.apiinterface.DataInterface.DateFormatChart;

public class RatePerwaktuChart extends AppCompatActivity {
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float yvalue;
    String gedung, waktu1, waktu2, tglIni, nama;
    LimitLine upper, lower;
    Calendar mCalendar;
    List<DataVolumeRatePerwaktu> dataRateRealtimes;
    Debit dataRate;
    public ArrayList<String> listRate;
    public ArrayList<String> listWaktu;
    List<RateItem> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_perwaktu_chart);
        lineChart = findViewById(R.id.chart);
        lineChart.setNoDataText("Data Grafik Tidak Tersedia.");
        lineChart.setNoDataTextColor(Color.rgb(3,169,244));

        Bundle bundle = getIntent().getExtras();
        nama = bundle.getString("nama");
        gedung = bundle.getString("gedung");
        waktu1 = bundle.getString("waktu1");
        waktu2 = bundle.getString("waktu2");


        Date c = Calendar.getInstance().getTime();
        mCalendar = Calendar.getInstance();
        tglIni = DateDataFormat.format(c).toString();
        getData(gedung, waktu1, waktu2);
    }
    public void getData(String gedung, String waktu1, String waktu2){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<VolumePerwaktuResponse> call = service.getVolumeAir(gedung, waktu1, waktu2);
        ArrayList<Entry> DataVals = new ArrayList<Entry>();
        call.enqueue(new Callback<VolumePerwaktuResponse>() {
            @Override
            public void onResponse(Call<VolumePerwaktuResponse> call, Response<VolumePerwaktuResponse> response) {
                listRate = new ArrayList<>();
                listWaktu = new ArrayList<>();

                if (response.body().isSuccess()){
                    if (response.body().getData() != null) {
                        dataList = (List<RateItem>) response.body().getData().getRate();
                        for (int i = 0; i < dataList.size(); i++){
                            RateItem x = dataList.get(i);
                            Float air = Float.parseFloat(x.getRate());
                            if (gedung.equals("rateP")){
                                lineDataSet.setLabel("Volume Gedung Pusat");
                            }
                            else if (gedung.equals("rateA")){
                                lineDataSet.setLabel("Volume Gedung A");
                            }
                            else if (gedung.equals("rateB")){
                                lineDataSet.setLabel("Volume Gedung B");
                            }
                            else if (gedung.equals("rateC")){
                                lineDataSet.setLabel("Volume Gedung C");
                            }
                            else if (gedung.equals("rateD")){
                                lineDataSet.setLabel("Volume Gedung D");
                            }

                            Date newDate = null;
                            try {
                                newDate = DateFormat.parse(String.valueOf(dataList.get(i).getWaktu()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            DataVals.add(new Entry(newDate.getTime(), air));
                        }
                    }
                    else {

                    }
                }
                ShowChart(DataVals, nama);
            }

            @Override
            public void onFailure(Call<VolumePerwaktuResponse> call, Throwable t) {

            }
        });

    }
    private void ShowChart(ArrayList<Entry> DataVals, String nama){
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.my_marker_view);
        lineChart.setMarkerView(mv);

        YAxis leftaxisy = lineChart.getAxisLeft();
        leftaxisy.removeAllLimitLines();

//        leftaxisy.setAxisMaximum(100f);
//        leftaxisy.setAxisMinimum(0f);

        leftaxisy.enableGridDashedLine(10f,10f,0f);
        leftaxisy.setDrawZeroLine(true);
        leftaxisy.setDrawLimitLinesBehindData(true);
        leftaxisy.setLabelCount(7,false);
        leftaxisy.setDrawGridLines(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long)value);
                return DateFormatChart.format(date);
            }
        });
        int[] color = new int[]{3, 169, 244};

        lineDataSet.setValues(DataVals);
        lineDataSet.setDrawIcons(false);
        lineDataSet.setCircleColor(Color.rgb(3,169,244));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(0f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);
        lineDataSet.setFillColor(Color.rgb(3,169,244));
        lineDataSet.setColor(Color.rgb(3,169,244));

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        Description description = lineChart.getDescription();
        description.setText(nama);
        description.setTextSize(12f);

        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateX(2000, Easing.EaseInOutBounce);
        lineChart.invalidate();
    }

}