package com.example.ms.expensemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

public class Home extends AppCompatActivity {
     TextView textView;
      DatabaseHelper myDb;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int flag=0;
    Button income,expense,chart,reminder,budget,vincome,vexpense,tools;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        String uid=preferences.getString("uid",null);



        myDb = new DatabaseHelper(this);
        textView=(TextView)findViewById(R.id.tv_balance);
       /*  Cursor res = myDb.getbal();*/

        SQLiteDatabase db = myDb.getWritableDatabase();
        Cursor res = db.rawQuery("select * from balance_table where uid="+uid,null);

        if(res.getCount()!= 0) {
            res.moveToNext();
            String b= res.getString(0);

            textView.setText(b);


        }
        else
        {
            textView.setText("0");
        }






        income=(Button)findViewById(R.id.btn_income);
        expense=(Button)findViewById(R.id.btn_expense);

        chart=(Button)findViewById(R.id.btn_chart);


        reminder=(Button)findViewById(R.id.btn_reminder);

        budget=(Button)findViewById(R.id.btn_budget);
        vincome=(Button)findViewById(R.id.btn_view_income);
        vexpense=(Button)findViewById(R.id.btn_view_expense);

        tools=(Button)findViewById(R.id.tools);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Income.class);
              //  intent.putExtra("edit","false");
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Expense.class);
                //intent.putExtra("edit","false");
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Chart.class);
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Reminder.class);
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);
            }
        });

     budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Budget.class);
                intent.putExtra("flag","1");
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);
            }
        });

     vincome.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Home.this,View_income.class);
        startActivity(intent);
        Home.this.finish();
        overridePendingTransition(0, 0);

        }
        });
        vexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,View_expense.class);
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);

            }
        });


        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Tools.class);
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(0, 0);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            popup();

            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }
    private void popup()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("LogOut...!!!");
        builder.setMessage("Are You Sure to Logout...?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //add the function to perform here
                Intent intent=new Intent(Home.this,Login.class);

                startActivity(intent);

                Home.this.finish();
                editor.clear();
                editor.apply();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
