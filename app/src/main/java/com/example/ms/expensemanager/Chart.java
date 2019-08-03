package com.example.ms.expensemanager;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Chart extends AppCompatActivity {
    DatabaseHelper dbh;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series2;
    Spinner spinner, month, year;
    String period, mo, y,uid;
    TextView tvmonth, tvyear;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    //array for data points
    int a[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    int ad[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    int b[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int e[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int dbb[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int de[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //array for custom labels
    //String y[]=new String[] {"0","50", "100", "150","200", "250", "300","350","400"};
    //String y[]=new String[] {"0", "100","200", "300","400"};
    //String x[]=new String[] {"0","1/18", "2/18", "3/18","4/18", "5/18", "6/18","7/18"};
    // String x[]={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    // String x[]={"0","1","2","3","4","5","6","7","8","9","10"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        dbh = new DatabaseHelper(this);
        spinner = (Spinner) findViewById(R.id.graph_period);
        month = (Spinner) findViewById(R.id.graph_month);
        tvmonth = (TextView) findViewById(R.id.tv_month);
        tvyear = (TextView) findViewById(R.id.tv_year);
        year = (Spinner) findViewById(R.id.graph_year);


        String dmy = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());

        month.setSelection(Integer.valueOf(dmy)-1);

        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                draw();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                draw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                draw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // staticLabelsFormatter.setVerticalLabels(y);


//graph.getViewport().setScalable(true);
//graph.getViewport().setScrollable(true);

    }


    public void draw() {
        period = spinner.getSelectedItem().toString();

        if (period.equals("daily")) {
            int mcount = month.getSelectedItemPosition();
            y = year.getSelectedItem().toString();

            mcount += 1;
            String mm = String.valueOf(mcount);

            //make array null
            for (int k = 0; k < 31; k++) {
                dbb[k] = 0;
                de[k] = 0;
            }
            SQLiteDatabase db = dbh.getWritableDatabase();
            Cursor resi = db.rawQuery("select date_t,income from income_table where uid='"+uid+"'", null);
            Cursor rese = db.rawQuery("select date_t,exp from expense_table where uid='"+uid+"'", null);
            while (resi.moveToNext()) {
                String sd = resi.getString(0);
                String str[] = sd.split("/");
                int d = Integer.parseInt(str[0]);
                int m = Integer.parseInt(str[1]);
                int iy = Integer.parseInt(str[2]);
                String sy = "20" + iy;
                String sm = String.valueOf(m);
                // Toast.makeText(this, sm+"="+mm, Toast.LENGTH_SHORT).show();
                if (sm.equals(mm) && sy.equals(y)) {

                    dbb[d - 1] += Integer.valueOf(resi.getString(1));
                }


            }

            while (rese.moveToNext()) {
                String sd = rese.getString(0);
                String str[] = sd.split("/");
                int d = Integer.parseInt(str[0]);
                int m = Integer.parseInt(str[1]);
                int iy = Integer.parseInt(str[2]);
                String sy = "20" + iy;
                String sm = String.valueOf(m);
                if (sm.equals(mm) && sy.equals(y)) {
                    de[d - 1] += Integer.valueOf(rese.getString(1));
                }
            }


            GraphView graph = (GraphView) findViewById(R.id.graph);
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            month.setVisibility(View.VISIBLE);
            tvmonth.setVisibility(View.VISIBLE);
            mo = month.getSelectedItem().toString();
            String x[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
            staticLabelsFormatter.setHorizontalLabels(x);
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            series = new LineGraphSeries<>(data2(dbb));
            series2 = new LineGraphSeries<>(data2(de));


            series.setColor(Color.GREEN);
            series2.setColor(Color.RED);

            graph.removeAllSeries();
            graph.addSeries(series);
            graph.addSeries(series2);

        }
        if (period.equals("monthly")) {
            y = year.getSelectedItem().toString();
            for (int k = 0; k < 12; k++) {
                b[k] = 0;
                e[k] = 0;
            }
            SQLiteDatabase db = dbh.getWritableDatabase();
            Cursor resi = db.rawQuery("select date_t,income from income_table where uid='"+uid+"'", null);
            Cursor rese = db.rawQuery("select date_t,exp from expense_table where uid='"+uid+"'", null);
            while (resi.moveToNext()) {
                String sd = resi.getString(0);
                String str[] = sd.split("/");
                int m = Integer.parseInt(str[1]);
                int iy = Integer.parseInt(str[2]);
                String sy = "20" + iy;
                if (sy.equals(y)) {
                    b[m - 1] += Integer.valueOf(resi.getString(1));
                }


            }
            while (rese.moveToNext()) {
                String sd = rese.getString(0);
                String str[] = sd.split("/");
                int m = Integer.parseInt(str[1]);
                int iy = Integer.parseInt(str[2]);
                String sy = "20" + iy;
                if (sy.equals(y)) {
                    e[m - 1] += Integer.valueOf(rese.getString(1));
                }
            }


            GraphView graph = (GraphView) findViewById(R.id.graph);
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            month.setVisibility(View.GONE);
            tvmonth.setVisibility(View.GONE);
            String x[] = {"jan", "feb", "march", "april", "may", "june", "july", "aug", "sep", "oct", "nov", "des"};
            staticLabelsFormatter.setHorizontalLabels(x);
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            series = new LineGraphSeries<>(data(b));
            series2 = new LineGraphSeries<>(data(e));

            series.setColor(Color.GREEN);
            series2.setColor(Color.RED);

            graph.removeAllSeries();
            graph.addSeries(series2);
            graph.addSeries(series);



        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Chart.this, Home.class);
        startActivity(i);
        Chart.this.finish();
        overridePendingTransition(0, 0);

    }

    public DataPoint[] data(int[] b) {
        DataPoint[] values = new DataPoint[12];     //creating an object of type DataPoint[] of size 'n'
        for (int i = 0; i < 12; i++) {
            DataPoint v = new DataPoint(a[i], b[i]);
            //  Toast.makeText(this, ""+a[i], Toast.LENGTH_SHORT).show();
            values[i] = v;
        }
        return values;
    }

    public DataPoint[] data2(int[] b) {
        DataPoint[] values = new DataPoint[31];     //creating an object of type DataPoint[] of size 'n'
        for (int i = 0; i < 31; i++) {
            DataPoint v = new DataPoint(ad[i], b[i]);
            //  Toast.makeText(this, ""+a[i], Toast.LENGTH_SHORT).show();
            values[i] = v;
        }
        return values;
    }


}
