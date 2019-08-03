package com.example.ms.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class View_expense_details extends AppCompatActivity{
    String exp_id,text,id ,expense,payee,cat,des,ref,sdate,img;
    DatabaseHelper myDb;
    TextView tinc,tpay,tcat,tdes,tref,tdate,timg,tvrec;

    ImageView imgrec;
    View line;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expense_details);


        myDb = new DatabaseHelper(this);
        Intent myIntent = getIntent(); // gets the previously created intent
        exp_id = myIntent.getStringExtra("id");
        Cursor res = myDb.getExpenseWithID(exp_id);

        res.moveToNext();



        tinc=(TextView)findViewById(R.id.tv_idamount);
        tpay=(TextView)findViewById(R.id.tv_idpayee);
        tcat=(TextView)findViewById(R.id.tv_idcategory);
        tdes=(TextView)findViewById(R.id.tv_iddescription);
        tref=(TextView)findViewById(R.id.tv_idrefno);
        tdate=(TextView)findViewById(R.id.tv_iddate);
        imgrec=(ImageView)findViewById(R.id.view_exp_rec);
        line=(View)findViewById(R.id.line_exp);
        tvrec=(TextView)findViewById(R.id.tv_exp_rec);



        id=exp_id;
        expense=res.getString(1);
        payee=res.getString(2);
        cat=res.getString(3);
        des=res.getString(4);
        ref=res.getString(5);
        sdate=res.getString(6);
        img=res.getString(7);





        File f = new File("/mnt/sdcard/ExpManager/"+id+"exp.jpeg");
        //Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+ File.separator +inc_id+".jpeg");
        //  imgrec.setImageBitmap(bmp);
        if(f.exists())
        {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            imgrec.setImageBitmap(bmp);
        }
        else
        {
            line.setVisibility(View.GONE);
            tvrec.setVisibility(View.GONE);
        }

        tinc.setText(expense);
        tpay.setText(payee);
        tcat.setText(cat);
        tdes.setText(des);
        tref.setText(ref);
        tdate.setText(sdate);


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(View_expense_details.this,View_expense.class);

        startActivity(i);
        View_expense_details.this.finish();
        overridePendingTransition(0, 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.edit:
            //add the function to perform here
            Intent intent=new Intent(View_expense_details.this,Expense.class);
            //passing data for editing
            intent.putExtra("expense",expense);
            intent.putExtra("payee",payee);
            intent.putExtra("id",id);
            intent.putExtra("cat",cat);
            intent.putExtra("des",des);
            intent.putExtra("ref",ref);
            intent.putExtra("sdate",sdate);
            intent.putExtra("edit","true");
            startActivity(intent);
            overridePendingTransition(0, 0);

            View_expense_details.this.finish();
            return(true);
        case R.id.delete:
            DatabaseHelper delinc =new DatabaseHelper(this);
            String sid=String.valueOf(id);
            delinc.deleteExpense(sid,expense);
            Intent iintent=new Intent(View_expense_details.this,View_expense.class);
            startActivity(iintent);
            View_expense_details.this.finish();
            overridePendingTransition(0, 0);

    }
        return(super.onOptionsItemSelected(item));
    }

}
