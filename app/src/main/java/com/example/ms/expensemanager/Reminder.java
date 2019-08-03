package com.example.ms.expensemanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reminder extends AppCompatActivity{
    EditText amount,pay,da,ti;
    String a,p,d,t,uid,tp;
    DatabaseHelper dbh;
    Spinner stype;
    Calendar myCalendar;
    FloatingActionButton fab;
TextView payertype;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        amount=(EditText) findViewById(R.id.rem_amount);
        pay=(EditText)findViewById(R.id.rem_pay);
        da=(EditText)findViewById(R.id.rem_date);
        ti=(EditText)findViewById(R.id.rem_time);
        dbh=new DatabaseHelper(this);
        fab=(FloatingActionButton)findViewById(R.id.rem_ok);
stype=(Spinner)findViewById(R.id.type);
payertype=(TextView)findViewById(R.id.payertype);

        myCalendar = Calendar.getInstance();




        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);


        //create datepicker dialogue
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        //action for date click
        da.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Reminder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





ti.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final Calendar myCalender = Calendar.getInstance();
        final int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        final int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                    int minut = myCalender.get(Calendar.MINUTE);
                    Toast.makeText(Reminder.this, String.valueOf(hour)+":"+String.valueOf(minut), Toast.LENGTH_SHORT).show();
                    ti.setText(String.valueOf(hour)+":"+String.valueOf(minut));

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(Reminder.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
});


        stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tp=stype.getSelectedItem().toString();
                if(tp.equals("Debit"))
                {
                    payertype.setText("payer");
                }
                else if (tp.equals("Credit"))
                {
                    payertype.setText("payee");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // Toast.makeText(Reminder.this, "fab clicked", Toast.LENGTH_SHORT).show();
        a=amount.getText().toString();
        p=pay.getText().toString();
        d=da.getText().toString();
        t=ti.getText().toString();
        tp=stype.getSelectedItem().toString();

        if(d.equals(""))
        {
            da.setError("choose date");
        }
        else if(t.equals(""))
        {
            ti.setError("choose time");
        }

        else
        {
            SQLiteDatabase db2 = dbh.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("date",d);
            contentValues.put("time",t);
            contentValues.put("pay",p);
            contentValues.put("amount",a);
            contentValues.put("status","ACTIVE");
            contentValues.put("uid",uid);
            contentValues.put("type",tp);
            db2.insert("reminder_table",null ,contentValues);


           // startService(new Intent(Reminder.this,ServiceClass.class));
            startService(new Intent(Reminder.this, ServiceClass.class));
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Reminder.this, ServiceClass.class);
            PendingIntent pintent = PendingIntent
                    .getService(Reminder.this, 0, intent, 0);

            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            // Start service every hour
            assert alarm != null;
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    60000, pintent);
            Toast.makeText(Reminder.this, "Reminder Added", Toast.LENGTH_SHORT).show();

            /*Snackbar.make(view, "Reminder Added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
            Intent i=new Intent(Reminder.this,Home.class);
            startActivity(i);
            Reminder.this.finish();
            overridePendingTransition(0, 0);
        }

    }
});


    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Reminder.this,Home.class);
        startActivity(i);
        Reminder.this.finish();
        overridePendingTransition(0, 0);
    }
    //update label of date after selection from  datepicker
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        da.setText(sdf.format(myCalendar.getTime()));
    }
}
