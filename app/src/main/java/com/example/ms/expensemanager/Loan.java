package com.example.ms.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Loan extends AppCompatActivity {
 EditText loanamount,interest,month;
 TextView result;
    double monthlyPayment=0.0;
    Button ok,cancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan);
        ok=(Button)findViewById(R.id.btn_ok);
        cancel=(Button)findViewById(R.id.btn_cancel);
        loanamount=(EditText)findViewById(R.id.tv_loan_amount);
        interest=(EditText)findViewById(R.id.et_interest_rate);

        month=(EditText)findViewById(R.id.et_loan_month);
        result=(TextView)findViewById(R.id.tv_result);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double loan,interestRate,loanperiod;

                if(!loanamount.getText().toString().equals("")&&!interest.getText().toString().equals("")&&!month.getText().toString().equals(""))
                {



              loan=Integer.parseInt(loanamount.getText().toString());
               // interestRate = Integer.parseInt(interest.getText().toString());
                    interestRate=Double.valueOf(interest.getText().toString());
              //  double dyear = Integer.parseInt(year.getText().toString());
              loanperiod= Integer.parseInt(month.getText().toString());




                //double loanperiod=dmonth+(dyear*12);
                double r = interestRate/1200;
                double r1 = Math.pow(r+1,loanperiod);

                monthlyPayment = (double) ((r+(r/(r1-1))) * loan);
                double totalPayment = monthlyPayment * loanperiod;
                result.setText("monthly payment ="+String.valueOf(monthlyPayment)+"\ntotal payment="+String.valueOf(totalPayment));

            }
            else
                {
                    if(loanamount.getText().toString().equals(""))
                    {
                        loanamount.setError("enter amount");
                    }
                  else  if(interest.getText().toString().equals(""))
                    {
                        interest.setError("enter amount");
                    }
                  else  if(month.getText().toString().equals(""))
                    {
                        month.setError("enter amount");
                    }

                }

            }
        });

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        loanamount.setText("");
        interest.setText("");
        month.setText("");
        result.setText("");
    }
});

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.loanaddinc:
            //add the function to perform here

            Intent intent=new Intent(Loan.this,Income.class);
            String result="";
            if(monthlyPayment!=0.0)
            {
                result= String.format("%.0f",monthlyPayment);
            }
            else
            {
                result="";
            }

            intent.putExtra("result",result);
            startActivity(intent);
            Loan.this.finish();
            overridePendingTransition(0, 0);
            return(true);
        case R.id.loanaddexp:
            //add the function to perform here

            Intent intente=new Intent(Loan.this,Expense.class);
            String resulte="";
            if(monthlyPayment!=0.0)
            {
                resulte = String.format("%.0f",monthlyPayment);
            }
            else
            {
                result="";
            }
            intente.putExtra("result",resulte);
            startActivity(intente);
            Loan.this.finish();
            overridePendingTransition(0, 0);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }


    @Override
    public void onBackPressed() {
        Intent i=new Intent(Loan.this,Tools.class);


        startActivity(i);
       Loan.this.finish();
        overridePendingTransition(0, 0);

    }
}
