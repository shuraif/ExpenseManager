package com.example.ms.expensemanager;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class View_expense extends AppCompatActivity {
    DatabaseHelper dbh;

    String sviewby,spayee,sdate,schoose;
    TextView amount,payee,date,tv_choose,tv_date;
    EditText et_date;
    DatabaseHelper myDb;
    ListView listView;
    TextView textView;
    List<HashMap<String,String>> lis;
TableLayout tl;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String uid;
    View line;


    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> clist = new ArrayList<String>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> cadapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expense);



        cadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clist);

        dbh = new DatabaseHelper(this);
        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();
        uid=preferences.getString("uid",null);

        myDb = new DatabaseHelper(this);

        line=(View)findViewById(R.id.line);
        tl=(TableLayout)findViewById(R.id.table);

        textView = (TextView) findViewById(R.id.empty_exp);






        listView = (ListView) findViewById(R.id.lst_exp);
        lis = new ArrayList<HashMap<String, String>>();

        //Toast.makeText(this, "selected"+sviewby, Toast.LENGTH_SHORT).show();










        fetchdata();
        SimpleAdapter adapter = new SimpleAdapter(View_expense.this, lis, R.layout.list_item, new String[]{"id","amount", "cat", "date"}, new int[]{R.id.tv_inc_id,R.id.inc, R.id.cat, R.id.date});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {



                TextView tv_id = (TextView) view.findViewById(R.id.tv_inc_id);
                String idtext=tv_id.getText().toString();
                Toast.makeText(View_expense.this, idtext, Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(View_expense.this,View_expense_details.class);
                intent.putExtra("id",idtext);

               startActivity(intent);

                View_expense.this.finish();
                overridePendingTransition(0, 0);




            }
        });


    }
        /*
        Cursor res = myDb.getAllexpense();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
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
            Cursor res = myDb.getAllexpense(uid);
            if(res.getCount() == 0) {
                // show message
                textView.setVisibility(View.VISIBLE);
                tl.setVisibility(View.INVISIBLE);
                line.setVisibility(View.INVISIBLE);
            }
            else
                {
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
        Intent i=new Intent(View_expense.this,Home.class);
        startActivity(i);
        View_expense.this.finish();
        overridePendingTransition(0, 0);
    }
    }
