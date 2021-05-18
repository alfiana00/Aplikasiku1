package com.example.aplikasiku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiku.ClaimsXAxisValueFormatter;
import com.example.aplikasiku.IndexAxisValueFormatter;
import com.example.aplikasiku.MyMarkerView;
import com.example.aplikasiku.R;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.apiinterface.DataInterface;
import com.example.aplikasiku.fragment.ChartA;
import com.example.aplikasiku.fragment.ChartB;
import com.example.aplikasiku.fragment.ChartC;
import com.example.aplikasiku.fragment.ChartD;
import com.example.aplikasiku.fragment.ChartPusat;
import com.example.aplikasiku.model.ChartRateRes;
import com.example.aplikasiku.model.DataRateRealtime;
import com.example.aplikasiku.model.DataVolume;
import com.example.aplikasiku.model.RateRealtimeResponse;
import com.example.aplikasiku.model.RealtimeResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateDataFormat;
import static com.example.aplikasiku.apiinterface.DataInterface.DateFormatChart;
import static com.example.aplikasiku.apiinterface.DataInterface.formatwaktu;
import static com.example.aplikasiku.apiinterface.DataInterface.myDateFormat;

public class RateRealtimeChart extends AppCompatActivity {

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
    public ArrayList<String> listRateA;
    public ArrayList<String> listRateB;
    public ArrayList<String> listRateC;
    public ArrayList<String> listRateD;
    public ArrayList<String> listRateP;
    public ArrayList<String> listWaktu;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_realtime_chart);

        tabLayout = findViewById(R.id.tabLayoutKode);
        viewPager = findViewById(R.id.viewPagerKode);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        //custom color icon and text tab layout
        index= tabLayout.getSelectedTabPosition();
        ((TextView) tabLayout.getTabAt(index).getCustomView()).setTextColor(getResources().getColor(R.color.white));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Date c = Calendar.getInstance().getTime();
        mCalendar = Calendar.getInstance();
        tglIni = myDateFormat.format(c).toString();


    }

    private void setupTabIcons() {
        TextView tv_chart_a = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tv_chart_a.setText("Chart A");
        tv_chart_a.setTextSize(16);
        tabLayout.getTabAt(0).setCustomView(tv_chart_a);

        TextView tv_chart_b = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tv_chart_b.setText("Chart B");
        tv_chart_b.setTextSize(16);
        tabLayout.getTabAt(1).setCustomView(tv_chart_b);

        TextView tv_chart_c = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tv_chart_c.setText("Chart C");
        tv_chart_c.setTextSize(16);
        tabLayout.getTabAt(2).setCustomView(tv_chart_c);

        TextView tv_chart_d = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tv_chart_d.setText("Chart D");
        tv_chart_d.setTextSize(16);
        tabLayout.getTabAt(3).setCustomView(tv_chart_d);

        TextView tv_chart_p = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tv_chart_p.setText("Chart Pusat");
        tv_chart_p.setTextSize(16);
        tabLayout.getTabAt(4).setCustomView(tv_chart_p);

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new ChartA(),"Chart A");
        viewPagerAdapter.addFrag(new ChartB(),"Chart B");
        viewPagerAdapter.addFrag(new ChartC(),"Chart C");
        viewPagerAdapter.addFrag(new ChartD(),"Chart D");
        viewPagerAdapter.addFrag(new ChartPusat(),"Chart Pusat");
        viewPager.setAdapter(viewPagerAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getDataRateAll(String column, String table,List<ChartRateRes.Datum> list){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<ChartRateRes> call = service.getRateChart(column,table);
        call.enqueue(new Callback<ChartRateRes>() {
            @Override
            public void onResponse(Call<ChartRateRes> call, Response<ChartRateRes> response) {
                if(response.code() == 200){
                    for(ChartRateRes.Datum data : response.body().getData()){
                        list.add(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChartRateRes> call, Throwable t) {

            }
        });
    }
}