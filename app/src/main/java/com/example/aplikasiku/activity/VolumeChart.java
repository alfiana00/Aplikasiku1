package com.example.aplikasiku.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiku.MyMarkerView;
import com.example.aplikasiku.R;
import com.example.aplikasiku.apihelper.RetrofitClient;
import com.example.aplikasiku.apiinterface.BaseApiService;
import com.example.aplikasiku.model.DataRate;
import com.example.aplikasiku.model.DataRateRealtime;
import com.example.aplikasiku.model.DataVolume;
import com.example.aplikasiku.model.RateRealtimeResponse;
import com.example.aplikasiku.model.VolumeResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplikasiku.apiinterface.DataInterface.DateDataFormat;
import static com.example.aplikasiku.apiinterface.DataInterface.DateFormatChart;
import static com.example.aplikasiku.apiinterface.DataInterface.myDateFormat;

public class VolumeChart extends AppCompatActivity {
    LineChart lineChart;
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
    List<DataVolume> dataList;
    public ArrayList<String> listVolumeA;
    public ArrayList<String> listVolumeB;
    public ArrayList<String> listVolumeC;
    public ArrayList<String> listVolumeD;
    public ArrayList<String> listVolumeP;
    public ArrayList<String> listWaktu;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_chart);

        lineChart = findViewById(R.id.chart);
        lineChart.setNoDataText("Data Grafik Tidak Tersedia.");
        lineChart.setNoDataTextColor(Color.rgb(3,169,244));


        Date c = Calendar.getInstance().getTime();
        mCalendar = Calendar.getInstance();
        tglIni = myDateFormat.format(c).toString();
        getData();

    }
    public void getData(){
        BaseApiService service = RetrofitClient.getClient1().create(BaseApiService.class);
        Call<VolumeResponse> call = service.getVolumeChart();
        ArrayList<Entry> DataValsA = new ArrayList<Entry>();
        ArrayList<Entry> DataValsB = new ArrayList<Entry>();
        ArrayList<Entry> DataValsC = new ArrayList<Entry>();
        ArrayList<Entry> DataValsD = new ArrayList<Entry>();
        ArrayList<Entry> DataValsP = new ArrayList<Entry>();
        listVolumeA = new ArrayList<>();
        listVolumeB = new ArrayList<>();
        listVolumeC = new ArrayList<>();
        listVolumeD = new ArrayList<>();
        listVolumeP = new ArrayList<>();
        listWaktu = new ArrayList<>();
        call.enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                if (response.body().isSuccess()){
                    dataList = response.body().getData();
                    for (int i = 0; i < dataList.size(); i++){
                        listVolumeA.add(dataList.get(i).getVolA());
                        listVolumeB.add(dataList.get(i).getVolB());
                        listVolumeC.add(dataList.get(i).getVolC());
                        listVolumeD.add(dataList.get(i).getVolD());
                        listVolumeP.add(dataList.get(i).getVolP());
                        listWaktu.add(String.format(dataList.get(i).getWaktu(), myDateFormat));
                        Log.i("cekdata", String.format(dataList.get(i).getWaktu(), myDateFormat));
                    }

                    dataList = response.body().getData();
                    Log.i("adaa22", response.body().getData().toString());
                    for (int i = 0; i < dataList.size(); i++) {
                        DataVolume x = dataList.get(i);

                        lineDataSetA.setLabel("Volume Air A");
                        lineDataSetB.setLabel("Volume Air B");
                        lineDataSetC.setLabel("Volume Air C");
                        lineDataSetD.setLabel("Volume Air D");
                        lineDataSetP.setLabel("Volume Air Pusat");
                        upper = new LimitLine(10f, "Batas Atas");
                        lower = new LimitLine(0f, "Batas Bawah");
                        Float volA = Float.parseFloat(x.getVolA());
                        Float volB = Float.parseFloat(x.getVolB());
                        Float volC = Float.parseFloat(x.getVolC());
                        Float volD = Float.parseFloat(x.getVolD());
                        Float volP = Float.parseFloat(x.getVolP());

                        Date newDate = null;
                        try {
                            newDate = myDateFormat.parse(String.valueOf(x.getWaktu()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DataValsA.add(new Entry(newDate.getTime(), volA));
                        DataValsB.add(new Entry(newDate.getTime(), volB));
                        DataValsC.add(new Entry(newDate.getTime(), volC));
                        DataValsD.add(new Entry(newDate.getTime(), volD));
                        DataValsP.add(new Entry(newDate.getTime(), volP));

                    }
                }
                else {

                    DataValsA.add(null);
                    DataValsB.add(null);
                    DataValsC.add(null);
                    DataValsD.add(null);
                    DataValsP.add(null);
                }
                ShowChart(DataValsA, DataValsB, DataValsC, DataValsD, DataValsP);
            }


            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {

            }
        });

    }

    private void ShowChart(ArrayList<Entry> DataValsA, ArrayList<Entry> DataValsB, ArrayList<Entry> DataValsC, ArrayList<Entry> DataValsD, ArrayList<Entry> DataValsP){
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.my_marker_view);
        lineChart.setMarkerView(mv);

        YAxis leftaxisy = lineChart.getAxisLeft();
        leftaxisy.removeAllLimitLines();
//
//        leftaxisy.setAxisMaximum(f);
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

        lineDataSetA.setValues(DataValsA);
        lineDataSetA.setDrawIcons(false);
        lineDataSetA.setCircleColor(Color.rgb(3,169,244));
        lineDataSetA.setLineWidth(2f);
        lineDataSetA.setCircleRadius(4f);
        lineDataSetA.setDrawCircleHole(false);
        lineDataSetA.setValueTextSize(0f);
        lineDataSetA.setDrawFilled(true);
        lineDataSetA.setFormLineWidth(1f);
        lineDataSetA.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetA.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSetA.setFormSize(15.f);
        lineDataSetA.setFillColor(Color.rgb(3,169,244));
        lineDataSetA.setColor(Color.rgb(3,169,244));

        lineDataSetB.setValues(DataValsB);
        lineDataSetB.setDrawIcons(false);
        lineDataSetB.setCircleColor(Color.BLUE);
        lineDataSetB.setLineWidth(2f);
        lineDataSetB.setCircleRadius(4f);
        lineDataSetB.setDrawCircleHole(false);
        lineDataSetB.setValueTextSize(0f);
        lineDataSetB.setDrawFilled(true);
        lineDataSetB.setFormLineWidth(1f);
        lineDataSetB.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetB.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSetB.setFormSize(15.f);
        lineDataSetB.setFillColor(Color.BLUE);
        lineDataSetB.setColor(Color.BLUE);

        lineDataSetC.setValues(DataValsC);
        lineDataSetC.setDrawIcons(false);
        lineDataSetC.setCircleColor(Color.YELLOW);
        lineDataSetC.setLineWidth(2f);
        lineDataSetC.setCircleRadius(4f);
        lineDataSetC.setDrawCircleHole(false);
        lineDataSetC.setValueTextSize(0f);
        lineDataSetC.setDrawFilled(true);
        lineDataSetC.setFormLineWidth(1f);
        lineDataSetC.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetC.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSetC.setFormSize(15.f);
        lineDataSetC.setFillColor(Color.YELLOW);
        lineDataSetC.setColor(Color.YELLOW);

        lineDataSetD.setValues(DataValsD);
        lineDataSetD.setDrawIcons(false);
        lineDataSetD.setCircleColor(Color.CYAN);
        lineDataSetD.setLineWidth(2f);
        lineDataSetD.setCircleRadius(4f);
        lineDataSetD.setDrawCircleHole(false);
        lineDataSetD.setValueTextSize(0f);
        lineDataSetD.setDrawFilled(true);
        lineDataSetD.setFormLineWidth(1f);
        lineDataSetD.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetD.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSetD.setFormSize(15.f);
        lineDataSetD.setFillColor(Color.CYAN);
        lineDataSetD.setColor(Color.CYAN);

        lineDataSetP.setValues(DataValsP);
        lineDataSetP.setDrawIcons(false);
        lineDataSetP.setCircleColor(Color.MAGENTA);
        lineDataSetP.setLineWidth(2f);
        lineDataSetP.setCircleRadius(4f);
        lineDataSetP.setDrawCircleHole(false);
        lineDataSetP.setValueTextSize(0f);
        lineDataSetP.setDrawFilled(true);
        lineDataSetP.setFormLineWidth(1f);
        lineDataSetP.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetP.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSetP.setFormSize(15.f);
        lineDataSetP.setFillColor(Color.MAGENTA);
        lineDataSetP.setColor(Color.MAGENTA);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSetA);
        iLineDataSets.add(lineDataSetB);
        iLineDataSets.add(lineDataSetC);
        iLineDataSets.add(lineDataSetD);
        iLineDataSets.add(lineDataSetP);
        lineData = new LineData(iLineDataSets);

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

    public LineDataSet setLineDataSet(ArrayList<Entry> DataVals, String color){
        LineDataSet l = new LineDataSet(null,null);
        return l;
    }
    public void pdfdownload(View view) {
        new SweetAlertDialog(VolumeChart.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Anda yakin untuk menyimpan data pemantauan Volume Air ?")
                .setConfirmText("Simpan")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                        progressDialog = new ProgressDialog(VolumeChart.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Memuat Data ...");
                        progressDialog.show();
                        Document document = new Document();
                        PdfPTable table = new PdfPTable(new float[] { 3, 1,1,1,1,1 });
                        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.getDefaultCell().setFixedHeight(20);
                        table.addCell("Waktu");
                        table.addCell("Gedung A");
                        table.addCell("Gedung B");
                        table.addCell("Gedung C");
                        table.addCell("Gedung D");
                        table.addCell("Gedung P");
                        table.setHeaderRows(1);
                        PdfPCell[] cells = table.getRow(0).getCells();
                        for (int j=0;j<cells.length;j++){
                            BaseColor myColor = WebColors.getRGBColor("#87D2F3");
                            cells[j].setBackgroundColor(myColor);
                        }
                        for (int i=0;i<listWaktu.size();i++){

                            Log.i("dddd", listWaktu.get(i));

                            table.addCell(listWaktu.get(i));
                            table.addCell(listVolumeA.get(i));
                            table.addCell(listVolumeB.get(i));
                            table.addCell(listVolumeC.get(i));
                            table.addCell(listVolumeD.get(i));
                            table.addCell(listVolumeP.get(i));
                        }
                        try {
                            File folder = new File(Environment.getExternalStorageDirectory()+ "/Fluid");
                            if (!folder.exists())
                                folder.mkdir();
                            final String pdf = folder.toString() + "/Volume Air_" +tglIni+ ".pdf";
                            PdfWriter.getInstance(document, new FileOutputStream(pdf));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.open();
                        try {

                            document.add(JudulText("Data Pemantauan Volume Air"));
                            document.add(JudulText(tglIni));
                            document.add(table);
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.close();
                        progressDialog.dismiss();

                        sweetAlertDialog.dismissWithAnimation();
                        Toast.makeText(VolumeChart.this, "Data pemantauan Volume Air Tanggal "+tglIni+" berhasil disimpan , Silahkan Lihat di Penyimpanan internal /Fluid", Toast.LENGTH_LONG).show();
                    }

                })
                .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();


    }
    public Paragraph JudulText(String text){
        Font mOrderDetailsTitleFont = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk mOrderDetailsTitleChunk = new Chunk(text, mOrderDetailsTitleFont);
        Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
        mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
        mOrderDetailsTitleParagraph.setSpacingAfter(7);
        return mOrderDetailsTitleParagraph;
    }
}