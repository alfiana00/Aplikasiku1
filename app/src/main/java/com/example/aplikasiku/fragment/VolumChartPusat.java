package com.example.aplikasiku.fragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aplikasiku.R;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRateRealtime;
import com.example.aplikasiku.model.VolumeResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateFormatChart;
import static com.example.aplikasiku.apiinterface.DataInterface.myDateFormat;

public class VolumChartPusat extends Fragment {
    LineChart lineChart;
    List<DataRateRealtime> dataRateRealtimes = new ArrayList<>();
    LineDataSet lineDataSet = new LineDataSet(null,null);
    LimitLine upper, lower;
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_volum_chart_pusat, container, false);
        lineChart = v.findViewById(R.id.chart);

        lineDataSet.setLabel("Volume Air C");

        getDataVolume();
        return v;
    }


    void getDataVolume(){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<VolumeResponse> call = service.getVolumChart("5");
        call.enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                ArrayList<Entry> DataValsA = new ArrayList<Entry>();
                List<VolumeResponse.Datum> datumList = new ArrayList<>();
                if(response.code() == 200){
                    for(VolumeResponse.Datum data : response.body().getData()){
                        Float VolA = Float.parseFloat(data.getVol());

                        Date newDate = null;
                        try {
                            newDate = myDateFormat.parse(String.valueOf(data.getWaktu()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DataValsA.add(new Entry(newDate.getTime(),VolA));
                        datumList.add(data);
                    }
                    upper = new LimitLine(10f, "Batas Atas");
                    lower = new LimitLine(0f, "Batas Bawah");

                    YAxis leftaxisy = lineChart.getAxisLeft();
                    leftaxisy.removeAllLimitLines();

                    leftaxisy.enableGridDashedLine(10f,10f,0f);
                    //leftaxisy.setDrawZeroLine(true);
                    leftaxisy.setDrawLimitLinesBehindData(false);
                    leftaxisy.setLabelCount(response.body().getData().size(),true);
                    leftaxisy.setDrawGridLines(true);
                    leftaxisy.setDrawZeroLine(false);

                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.enableGridDashedLine(10f, 10f, 0f);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setGranularity(7f);
                    xAxis.setLabelRotationAngle(315f);
                    xAxis.setDrawGridLines(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setLabelCount(response.body().getData().size(),true);
                    xAxis.setDrawLimitLinesBehindData(true);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            Date date = new Date((long)value);
                            return DateFormatChart.format(date);
                        }
                    });

                    Log.i("logcc",new Gson().toJson(datumList));
                    lineDataSet.setValues(DataValsA);
                    lineDataSet.setDrawIcons(false);
                    lineDataSet.setCircleColor(Color.rgb(3,169,244));
                    lineDataSet.setLineWidth(2f);
                    lineDataSet.setCircleRadius(4f);
                    lineDataSet.setDrawCircleHole(false);
                    lineDataSet.setValueTextSize(0f);
                    lineDataSet.setDrawFilled(false);
                    lineDataSet.setFormLineWidth(1f);
                    lineDataSet.setMode(LineDataSet.Mode.LINEAR);
                    lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    lineDataSet.setFormSize(15.f);
                    lineDataSet.setFillColor(Color.rgb(3,169,244));
                    lineDataSet.setColor(Color.rgb(3,169,244));

                    lineData = new LineData(lineDataSet);
                    lineChart.setData(lineData);
                    lineChart.setTouchEnabled(true);
                    lineChart.setDragEnabled(true);
                    lineChart.setScaleEnabled(true);
                    lineChart.setPinchZoom(false);
                    lineChart.setDrawGridBackground(false);
                    lineChart.getDescription().setEnabled(false);
                    lineChart.getAxisRight().setEnabled(false);
                    lineChart.animateX(2000, Easing.EaseInOutBounce);
                    lineChart.invalidate();
                }
            }

            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}