package com.example.aplikasiku;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRateRealtime;
import com.example.aplikasiku.model.RateRealtimeResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateFormatChart;
import static com.example.aplikasiku.apiinterface.DataInterface.myDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartRateAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartRateAFragment extends Fragment {
    LineChart lineChart, lineChartB;
    LineDataSet lineDataSetA = new LineDataSet(null,null);
    LineDataSet lineDataSetB = new LineDataSet(null,null);
    LineDataSet lineDataSetC = new LineDataSet(null,null);
    LineDataSet lineDataSetD = new LineDataSet(null,null);
    LineDataSet lineDataSetP = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float yvalue;
    String extra, tglIni;
    LimitLine upper, lower;
    Calendar mCalendar;
    List<DataRateRealtime> dataRateRealtimes;
    public ArrayList<String> listRate;
    public ArrayList<String> listWaktu;
    ProgressDialog progressDialog;

    public ChartRateAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartRateAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartRateAFragment newInstance(String param1, String param2) {
        ChartRateAFragment fragment = new ChartRateAFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lineChart.setNoDataText("Data Grafik Tidak Tersedia.");
        lineChart.setNoDataTextColor(Color.rgb(3,169,244));

        lineChartB.setNoDataText("Data Grafik Tidak Tersedia.");
        lineChartB.setNoDataTextColor(Color.rgb(3,169,244));


        Date c = Calendar.getInstance().getTime();
        mCalendar = Calendar.getInstance();
        tglIni = myDateFormat.format(c).toString();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_rate_a, container, false);

        lineChart = view.findViewById(R.id.chart);
//        lineChartB = view.findViewById(R.id.chartB);
        // Inflate the layout for this fragment
        return view;
    }

    public void getData(){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<RateRealtimeResponse> call = service.getRealtimeRate();
        ArrayList<Entry> DataValsA = new ArrayList<Entry>();
        listRate = new ArrayList<>();
        listWaktu = new ArrayList<>();
        call.enqueue(new Callback<RateRealtimeResponse>() {
            @Override
            public void onResponse(Call<RateRealtimeResponse> call, Response<RateRealtimeResponse> response) {
                if (response.body().isSuccess()){
                    dataRateRealtimes = response.body().getData();
                    for (int i = 0; i < dataRateRealtimes.size(); i++){
                        listRate.add(dataRateRealtimes.get(i).getRateA());
                        listWaktu.add(String.format(dataRateRealtimes.get(i).getWaktu(), myDateFormat));
                    }
                    Log.i("adaa22", response.body().getData().toString());
                    for (int i = 0; i < dataRateRealtimes.size(); i++) {
                        DataRateRealtime x = dataRateRealtimes.get(i);

                        lineDataSetA.setLabel("Rate Air A");
                        upper = new LimitLine(10f, "Batas Atas");
                        lower = new LimitLine(0f, "Batas Bawah");
                        Float rateA = Float.parseFloat(x.getRateA());

                        Date newDate = null;
                        try {
                            newDate = DateFormatChart.parse(String.valueOf(x.getWaktu()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        DataValsA.add(new Entry(newDate.getTime(), rateA));

                    }
                }
                else {

                    DataValsA.add(null);
                }
                ShowChart(DataValsA);
            }
            private void ShowChart(ArrayList<Entry> DataVals){
                MyMarkerView mv = new MyMarkerView(getActivity().getApplicationContext(), R.layout.my_marker_view);
                lineChart.setMarkerView(mv);

                YAxis leftaxisy = lineChart.getAxisLeft();
                leftaxisy.removeAllLimitLines();

                leftaxisy.enableGridDashedLine(10f,10f,0f);
                //leftaxisy.setDrawZeroLine(true);
                leftaxisy.setDrawLimitLinesBehindData(false);
                leftaxisy.setLabelCount(7,false);
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
                xAxis.setLabelCount(6,true);
                xAxis.setDrawLimitLinesBehindData(true);
                xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        Date date = new Date((long)value);
                        return DateFormatChart.format(date);
                    }
                });

                int[] color = new int[]{3, 169, 244};

                lineDataSetA.setValues(DataValsA);
                lineDataSetA.setDrawIcons(false);
                lineDataSetA.setCircleColor(Color.rgb(3,169,244));
                lineDataSetA.setLineWidth(2f);
                lineDataSetA.setCircleRadius(4f);
                lineDataSetA.setDrawCircleHole(false);
                lineDataSetA.setValueTextSize(0f);
                lineDataSetA.setDrawFilled(false);
                lineDataSetA.setFormLineWidth(1f);
                lineDataSetA.setMode(LineDataSet.Mode.LINEAR);
                lineDataSetA.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                lineDataSetA.setFormSize(15.f);
                lineDataSetA.setFillColor(Color.rgb(3,169,244));
                lineDataSetA.setColor(Color.rgb(3,169,244));

                iLineDataSets.clear();
                iLineDataSets.add(lineDataSetA);
                lineData = new LineData(iLineDataSets);

                lineChart.clear();
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

            @Override
            public void onFailure(Call<RateRealtimeResponse> call, Throwable t) {

            }
        });

    }
}