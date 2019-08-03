package com.example.ms.expensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View_income extends AppCompatActivity {
    TextView amount,payer,date;
    DatabaseHelper myDb;
    ListView listView;
    TextView textView;
    List<HashMap<String,String>> lis;
    TableLayout tl;
    View line;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String uid;
    protected void  onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_income);



        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();
        uid=preferences.getString("uid",null);


        myDb = new DatabaseHelper(this);

      line=(View)findViewById(R.id.line);
        tl=(TableLayout)findViewById(R.id.table);
        textView=(TextView)findViewById(R.id.empty);
        listView = (ListView) findViewById(R.id.lst);
        lis = new ArrayList<HashMap<String, String>>();
        fetchdata();


        SimpleAdapter adapter = new SimpleAdapter(View_income.this, lis, R.layout.list_item, new String[]{"id","amount","cat","date"},new int[]{R.id.tv_inc_id,R.id.inc,R.id.cat,R.id.date});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {



                TextView tv_id = (TextView) view.findViewById(R.id.tv_inc_id);
                String idtext=tv_id.getText().toString();
               // Toast.makeText(View_income.this, idtext, Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(View_income.this,View_income_details.class);
                intent.putExtra("id",idtext);

                startActivity(intent);

                View_income.this.finish();
                overridePendingTransition(0, 0);





            }
        });











    }





/*        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("amount :"+ res.getString(1)+"\n");
            buffer.append("name :"+ res.getString(2)+"\n");
            buffer.append("Category :"+ res.getString(3)+"\n\n");
        }

        // Show all data
        showMessage("Data",buffer.toString());

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/


private void fetchdata()
{
    Cursor res = myDb.getAllData(uid);
    if(res.getCount() == 0) {
        // show message
       textView.setVisibility(View.VISIBLE);
        tl.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

    }else {
        textView.setVisibility(View.INVISIBLE);
        while (res.moveToNext()) {

            HashMap<String,String> map=new HashMap<String, String>();
            map.put("id",res.getString(0));
            map.put("amount",res.getString(1));
            map.put("cat",res.getString(3));
            map.put("date",res.getString(6));
           lis.add(map);
        }
    }





}
    @Override
    public void onBackPressed() {
        Intent i=new Intent(View_income.this,Home.class);


        startActivity(i);
        View_income.this.finish();
        overridePendingTransition(0, 0);

    }

}