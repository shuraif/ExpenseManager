package com.example.ms.expensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class New_budget extends AppCompatActivity {
    DatabaseHelper myDb;

    FloatingActionButton fab;
    EditText  am,al,da;
    Spinner cat,period;
    Calendar myCalendar;
    String id;
    String scat,speriod,samount,salert,dmy,uid;
    DatabaseHelper dbh;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_budget);


        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);


        myCalendar = Calendar.getInstance();
        myDb = new DatabaseHelper(this);

        fab = findViewById(R.id.btn_addbudget);

        cat=(Spinner)findViewById(R.id.bud_cat);

        period=(Spinner)findViewById(R.id.bud_period);
        dbh=new DatabaseHelper(this);
        am=(EditText)findViewById(R.id.bud_amount99);
        al=(EditText)findViewById(R.id.bud_alert99);
        da=(EditText)findViewById(R.id.bud_date1);


//for editing the budget
        Intent myIntent = getIntent();
        id=myIntent.getStringExtra("id");
        if(!id.equals(""))
        {
            Cursor res = myDb.getbudwithid(id);
            res.moveToNext();
          //  String eid=res.getString(0);
            String c=res.getString(1);


            if(c.equals("All"))
            {
                cat.setSelection(0);
            }
           else if(c.equals("Automobile"))
            {
                cat.setSelection(1);
            }
            else if(c.equals("Entertinement"))
            {
                cat.setSelection(2);
            }
            else if(c.equals("Family"))
            {
                cat.setSelection(3);
            }
            else if(c.equals("Food"))
            {
                cat.setSelection(4);
            }
            else if(c.equals("Helth Care"))
            {
                cat.setSelection(5);
            }
            else if(c.equals("Home"))
            {
                cat.setSelection(6);
            }
            else if(c.equals("Ofice"))
            {
                cat.setSelection(7);
            }
            else if(c.equals("Loan"))
            {
                cat.setSelection(8);
            }
            else if(c.equals("Travel"))
            {
                cat.setSelection(9);
            }
            else if(c.equals("Tax"))
            {
                cat.setSelection(10);
            }
            else if(c.equals("Insurance"))
            {
                cat.setSelection(11);
            }


            String p=res.getString(2);
            if(p.equals("daily"))
            {
                period.setSelection(0);
            }
            else if(p.equals("weekly"))
            {
                period.setSelection(1);
            }
            else if(p.equals("monthly"))
            {
                period.setSelection(2);
            }
            else if(p.equals("yearly"))
            {
                period.setSelection(3);
            }







            String amount=res.getString(3);
            String alert=res.getString(4);
            String date=res.getString(5);
            am.setText(amount);
            al.setText(alert);
            da.setText(date);
           //ok button action for upadation
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    scat = cat.getSelectedItem().toString();
                    speriod = period.getSelectedItem().toString();
                    samount = am.getText().toString();
                    salert = al.getText().toString();
                    dmy = da.getText().toString();
                    // dmy = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                    String dateInString = dmy;  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(dateInString));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (speriod.equals("daily")) {
                        c.add(Calendar.DATE, 0);
                    } else if (speriod.equals("weekly")) {
                        c.add(Calendar.DATE, 7);
                    } else if (speriod.equals("monthly")) {
                        c.add(Calendar.DATE, 30);
                    } else if (speriod.equals("yearly")) {
                        c.add(Calendar.DATE, 365);
                    }
                    sdf = new SimpleDateFormat("dd/MM/yy");
                    Date resultdate = new Date(c.getTimeInMillis());
                    dateInString = sdf.format(resultdate);
                    //Toast.makeText(New_budget.this, dateInString, Toast.LENGTH_SHORT).show();
                    samount = am.getText().toString();
                    if (samount.equals(""))
                    {
                        am.setError("enter amount");

                    }

                  else  if (salert.equals(""))
                    {
                        al.setError("enter amount");
                    }
                    else if(dmy.equals(""))
                    {
                        da.setError("choose date");
                    }
                    else if(Integer.valueOf(samount)<Integer.valueOf(salert))
                    {
                        al.setError("enterd alert amount is greaterthan Budget");

                    }
                    else
                        {

                      //  Toast.makeText(New_budget.this, ""+samount, Toast.LENGTH_SHORT).show();

                        //call update from here
                        //dbh.insertBudget(scat, speriod, samount, salert, dmy, dateInString);
                        boolean a = dbh.updatebudget(id, scat, speriod, samount, salert, dmy, dateInString);


                        if (a) {
                            Snackbar.make(view, "Budget Updated", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                        if (!a) {
                            Toast.makeText(New_budget.this, "update fail", Toast.LENGTH_SHORT).show();
                        }
                      Intent i = new Intent(New_budget.this, Budget.class);
                        i.putExtra("flag", "1");

                        startActivity(i);
                        New_budget.this.finish();
                        overridePendingTransition(0, 0);
                    }}

            });

        }
        else {

//ok b utton action for new insertion
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    scat = cat.getSelectedItem().toString();
                    speriod = period.getSelectedItem().toString();
                    samount = am.getText().toString();
                    salert = al.getText().toString();
                    dmy = da.getText().toString();
                    // dmy = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                    String dateInString = dmy;  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(dateInString));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (speriod.equals("daily")) {
                        c.add(Calendar.DATE, 0);
                    } else if (speriod.equals("weekly")) {
                        c.add(Calendar.DATE, 6);
                    } else if (speriod.equals("monthly")) {
                        c.add(Calendar.DATE, 30);
                    } else if (speriod.equals("yearly")) {
                        c.add(Calendar.DATE, 365);
                    }
                    sdf = new SimpleDateFormat("dd/MM/yy");
                    Date resultdate = new Date(c.getTimeInMillis());
                    dateInString = sdf.format(resultdate);
                    //Toast.makeText(New_budget.this, dateInString, Toast.LENGTH_SHORT).show();

                    if (samount.equals("")) {
                        am.setError("enter amount");

                    }

                   else if (salert.equals("")) {
                        al.setError("enter amount");
                    }
                    else if(dmy.equals(""))
                    {
                        da.setError("Choose date");
                    }
                    else if(Integer.valueOf(samount)<Integer.valueOf(salert))
                    {
                        al.setError("enterd alert amount is greaterthan Budget");

                    }
                    else
                    {


                            // Toast.makeText(New_budget.this,"start date"+ dmy, Toast.LENGTH_LONG).show();
                            // Toast.makeText(New_budget.this,"end date"+ dateInString, Toast.LENGTH_LONG).show();
                            boolean a = dbh.insertBudget(scat, speriod, samount, salert, dmy, dateInString, uid);


                            if (a) {
                                Snackbar.make(view, "Budget Created", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                            if (!a) {
                                Toast.makeText(New_budget.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                            Intent i = new Intent(New_budget.this, Budget.class);
                            i.putExtra("flag", "1");

                            startActivity(i);
                            New_budget.this.finish();
                            overridePendingTransition(0, 0);
                        }}


            });
        }



        //create datepicker dialogue
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

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
                new DatePickerDialog(New_budget.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        da.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(New_budget.this,Budget.class);
        i.putExtra("flag","1");

        startActivity(i);
        New_budget .this.finish();
        overridePendingTransition(0, 0);

    }
}
