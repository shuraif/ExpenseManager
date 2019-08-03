package com.example.ms.expensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Budget extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listView;
    Button d,w,m,y;
    int flag=0;
    String alert;
    String bal,uid;




    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    List<HashMap<String,String>> lis;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget);


        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);


        d=(Button) findViewById(R.id.daily);
        w=(Button)findViewById(R.id.weekly);
        m=(Button)findViewById(R.id.monthly);
        y=(Button)findViewById(R.id.yearly);
        listView=(ListView)findViewById(R.id.lst_bud);
        myDb = new DatabaseHelper(this);

        lis = new ArrayList<HashMap<String, String>>();
        Intent intent = getIntent();

        flag=Integer.valueOf( intent.getStringExtra("flag"));


        if(flag==1){
            flag=1;
            d.setBackgroundColor(Color.GREEN);
            //  Toast.makeText(Budget.this, "daily", Toast.LENGTH_SHORT).show();

            Cursor res = myDb.getbud("daily",uid);
            if(res.getCount() == 0) {
                // show message
                // textView.setVisibility(View.VISIBLE);
                Toast.makeText(Budget.this, "no data", Toast.LENGTH_SHORT).show();

            }
            else {
                //textView.setVisibility(View.INVISIBLE);
                while (res.moveToNext()) {

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("cat",res.getString(1));
                    map.put("total",res.getString(3));
                   alert=res.getString(4);
                    map.put("alert",alert);
                   int spend=Integer.valueOf(res.getString(6));
                   int  balance=Integer.valueOf(res.getString(3))-spend;
                   bal=String.valueOf(balance);
                    map.put("balance",String.valueOf(balance));
                    map.put("id",res.getString(0));
                    map.put("spend",res.getString(6));
                    //  map.put("alert",res.getString(4));
                   /* Toast.makeText(Budget.this, res.getString(1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(2), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(3), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(4), Toast.LENGTH_SHORT).show();*/
                    lis.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(Budget.this, lis, R.layout.budget_list, new String[]{"cat","total","alert","balance","id","spend"},new int[]{R.id.bud_category,R.id.total_budget,R.id.bud_alert,R.id.bud_remaining,R.id.bud_id,R.id.bud_spend});
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
        else if(flag==2)
        {
            //  Toast.makeText(Budget.this, "daily", Toast.LENGTH_SHORT).show();
            w.setBackgroundColor(Color.GREEN);
            Cursor res = myDb.getbud("weekly",uid);
            if(res.getCount() == 0) {
                // show message
                // textView.setVisibility(View.VISIBLE);
                Toast.makeText(Budget.this, "no data", Toast.LENGTH_SHORT).show();

            }
            else {
                //textView.setVisibility(View.INVISIBLE);
                while (res.moveToNext()) {

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("cat",res.getString(1));
                    map.put("total",res.getString(3));
                    map.put("alert",res.getString(4));
                    int spend=Integer.valueOf(res.getString(6));
                    Toast.makeText(this, ""+spend, Toast.LENGTH_SHORT).show();
                    int  balance=Integer.valueOf(res.getString(3))-spend;
                    bal=String.valueOf(balance);
                    map.put("balance",String.valueOf(balance));
                    map.put("id",res.getString(0));
                    //  map.put("alert",res.getString(4));
                  /*  Toast.makeText(Budget.this, res.getString(1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(2), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(3), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(4), Toast.LENGTH_SHORT).show();*/
                    lis.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(Budget.this, lis, R.layout.budget_list, new String[]{"cat","total","alert","balance","id"},new int[]{R.id.bud_category,R.id.total_budget,R.id.bud_alert,R.id.bud_remaining,R.id.bud_id});
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
        else if(flag==3)
        {
            //  Toast.makeText(Budget.this, "daily", Toast.LENGTH_SHORT).show();
            m.setBackgroundColor(Color.GREEN);
            Cursor res = myDb.getbud("monthly",uid);
            if(res.getCount() == 0) {
                // show message
                // textView.setVisibility(View.VISIBLE);
                Toast.makeText(Budget.this, "no data", Toast.LENGTH_SHORT).show();

            }
            else {
                //textView.setVisibility(View.INVISIBLE);
                while (res.moveToNext()) {

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("cat",res.getString(1));
                    map.put("total",res.getString(3));
                    map.put("alert",res.getString(4));
                    int spend=Integer.valueOf(res.getString(6));
                    int  balance=Integer.valueOf(res.getString(3))-spend;
                    bal=String.valueOf(balance);
                    map.put("balance",String.valueOf(balance));
                    map.put("id",res.getString(0));
                    //  map.put("alert",res.getString(4));
                    /*Toast.makeText(Budget.this, res.getString(1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(2), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(3), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(4), Toast.LENGTH_SHORT).show();*/
                    lis.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(Budget.this, lis, R.layout.budget_list, new String[]{"cat","total","alert","balance","id"},new int[]{R.id.bud_category,R.id.total_budget,R.id.bud_alert,R.id.bud_remaining,R.id.bud_id});
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
        else if(flag==4)
        {
            //  Toast.makeText(Budget.this, "daily", Toast.LENGTH_SHORT).show();
            y.setBackgroundColor(Color.GREEN);
            Cursor res = myDb.getbud("yearly",uid);
            if(res.getCount() == 0) {
                // show message
                // textView.setVisibility(View.VISIBLE);
              //  Toast.makeText(Budget.this, "no data", Toast.LENGTH_SHORT).show();

            }
            else {
                //textView.setVisibility(View.INVISIBLE);
                while (res.moveToNext()) {

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("cat",res.getString(1));
                    map.put("total",res.getString(3));
                    map.put("alert",res.getString(4));
                    int spend=Integer.valueOf(res.getString(6));
                    int  balance=Integer.valueOf(res.getString(3))-spend;
                    bal=String.valueOf(balance);
                    map.put("balance",String.valueOf(balance));

                    map.put("id",res.getString(0));
                    //  map.put("alert",res.getString(4));
                   /* Toast.makeText(Budget.this, res.getString(1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(2), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(3), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Budget.this, res.getString(4), Toast.LENGTH_SHORT).show();*/
                    lis.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(Budget.this, lis, R.layout.budget_list, new String[]{"cat","total","alert","balance","id"},new int[]{R.id.bud_category,R.id.total_budget,R.id.bud_alert,R.id.bud_remaining,R.id.bud_id});
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }


//editing buget.pass ing id to new budget page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView bud_id = (TextView) view.findViewById(R.id.bud_id);
                String idtext=bud_id.getText().toString();

                Intent intent=new Intent(Budget.this,New_budget.class);
                intent.putExtra("id",idtext);
                Toast.makeText(Budget.this, idtext, Toast.LENGTH_SHORT).show();

                startActivity(intent);
                Budget.this.finish();;
                overridePendingTransition(0, 0);



            }
        });





//daily budgets
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Budget.this,Budget.class);
                intent.putExtra("flag","1");
                startActivity(intent);
                Budget.this.finish();
                overridePendingTransition(0, 0);

              }
        });



        //weekly budget
w.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(Budget.this,Budget.class);
        intent.putExtra("flag","2");
        startActivity(intent);
        Budget.this.finish();
        overridePendingTransition(0, 0);


    }
});

//monthly budget
m.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(Budget.this,Budget.class);

        intent.putExtra("flag","3");
        startActivity(intent);
        Budget.this.finish();
        overridePendingTransition(0, 0);
    }
});

y.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Budget.this,Budget.class);
        intent.putExtra("flag","4");
        startActivity(intent);
        Budget.this.finish();
        overridePendingTransition(0, 0);
    }
});
    }






    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        int swipe;
                        if(flag>1)
                        {
                            swipe=flag-1;
                            Intent intent=new Intent(Budget.this,Budget.class);
                            intent.putExtra("flag",String.valueOf(swipe));
                            startActivity(intent);
                            Budget.this.finish();
                            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
                        }

                       // Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }

                    // Right to left swipe action
                    else
                    {
                        int swipe;
                        if(flag<4)
                        {
                            swipe=flag+1;
                            Intent intent=new Intent(Budget.this,Budget.class);
                            intent.putExtra("flag",String.valueOf(swipe));
                            startActivity(intent);
                            Budget.this.finish();
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }


                       // Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here

            Intent intent=new Intent(Budget.this,New_budget.class);
            intent.putExtra("id","");
            startActivity(intent);
            Budget.this.finish();
            overridePendingTransition(0, 0);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Budget.this,Home.class);


        startActivity(i);
        Budget.this.finish();
        overridePendingTransition(0, 0);

    }
}
