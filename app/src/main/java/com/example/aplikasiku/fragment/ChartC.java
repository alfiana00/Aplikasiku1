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
import com.example.aplikasiku.model.ChartRateRes;
import com.example.aplikasiku.model.DataRateRealtime;
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

public class ChartC extends Fragment {
    LineChart lineChartA;
    List<DataRateRealtime> dataRateRealtimes = new ArrayList<>();
    LineDataSet lineDataSetC = new LineDataSet(null,null);
    LimitLine upper, lower;
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart_c, container, false);
        lineChartA = v.findViewById(R.id.chart);

        lineDataSetC.setLabel("Rate Air C");

        getDataVolum();
        return v;
    }


    void getDataVolum(){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<ChartRateRes> call = service.getRateChart("rateC","rate_c");
        call.enqueue(new Callback<ChartRateRes>() {
            @Override
            public void onResponse(Call<ChartRateRes> call, Response<ChartRateRes> response) {
                ArrayList<Entry> DataValsA = new ArrayList<Entry>();
                List<ChartRateRes.Datum> datumList = new ArrayList<>();
                if(response.code() == 200){
                    for(ChartRateRes.Datum data : response.body().getData()){
                        Float rateA = Float.parseFloat(data.getRate());

                        Date newDate = null;
                        try {
                            newDate = DateFormatChart.parse(String.valueOf(data.getWaktu()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DataValsA.add(new Entry(newDate.getTime(),rateA));
                        datumList.add(data);
                    }
                    upper = new LimitLine(10f, "Batas Atas");
                    lower = new LimitLine(0f, "Batas Bawah");

                    YAxis leftaxisy = lineChartA.getAxisLeft();
                    leftaxisy.removeAllLimitLines();

                    leftaxisy.enableGridDashedLine(10f,10f,0f);
                    //leftaxisy.setDrawZeroLine(true);
                    leftaxisy.setDrawLimitLinesBehindData(false);
                    leftaxisy.setLabelCount(response.body().getData().size(),true);
                    leftaxisy.setDrawGridLines(true);
                    leftaxisy.setDrawZeroLine(false);

                    XAxis xAxis = lineChartA.getXAxis();
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

                    Log.i("asdsad",new Gson().toJson(datumList));
                    lineDataSetC.setValues(DataValsA);
                    lineDataSetC.setDrawIcons(false);
                    lineDataSetC.setCircleColor(Color.rgb(3,169,244));
                    lineDataSetC.setLineWidth(2f);
                    lineDataSetC.setCircleRadius(4f);
                    lineDataSetC.setDrawCircleHole(false);
                    lineDataSetC.setValueTextSize(0f);
                    lineDataSetC.setDrawFilled(false);
                    lineDataSetC.setFormLineWidth(1f);
                    lineDataSetC.setMode(LineDataSet.Mode.LINEAR);
                    lineDataSetC.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    lineDataSetC.setFormSize(15.f);
                    lineDataSetC.setFillColor(Color.rgb(3,169,244));
                    lineDataSetC.setColor(Color.rgb(3,169,244));

                    lineData = new LineData(lineDataSetC);
                    lineChartA.setData(lineData);
                    lineChartA.setTouchEnabled(true);
                    lineChartA.setDragEnabled(true);
                    lineChartA.setScaleEnabled(true);
                    lineChartA.setPinchZoom(false);
                    lineChartA.setDrawGridBackground(false);
                    lineChartA.getDescription().setEnabled(false);
                    lineChartA.getAxisRight().setEnabled(false);
                    lineChartA.animateX(2000, Easing.EaseInOutBounce);
                    lineChartA.invalidate();
                }else{
                    Toast.makeText(getContext(), ""+response.code()+"//"+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChartRateRes> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}