package com.example.ms.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Tools extends AppCompatActivity {
    Button notes,calculator,currency,loan,backup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools);



        notes=(Button)findViewById(R.id.btn_notes);
        currency=(Button)findViewById(R.id.btn_currency);
        loan=(Button)findViewById(R.id.btn_loan);
        calculator=(Button)findViewById(R.id.btn_cal);
        backup=(Button)findViewById(R.id.btn_backup);


        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tools.this,Notes.class);
                startActivity(intent);
                Tools.this.finish();
                overridePendingTransition(0, 0);
            }
        });


        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tools.this,Currencycon.class);
                startActivity(intent);
                Tools.this.finish();
                overridePendingTransition(0, 0);
            }
        });
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tools.this,Loan.class);
                startActivity(intent);
                Tools.this.finish();
                overridePendingTransition(0, 0);
            }
        });


        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tools.this,Calc.class);
                startActivity(intent);
                Tools.this.finish();
                overridePendingTransition(0, 0);
            }
        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tools.this,Backuprestore.class);
                startActivity(intent);
               Tools.this.finish();
                overridePendingTransition(0, 0);
            }
        });




    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Tools.this,Home.class);
        startActivity(i);
        Tools.this.finish();
        overridePendingTransition(0, 0);
    }
}
