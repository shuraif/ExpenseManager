package com.example.ms.expensemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ms.expensemanager.NetworkChangeReceiver.isOnline;


public class Currencycon extends AppCompatActivity {
    double convvalue=0.0;
    public String s,day,p1;
    ProgressDialog pd;
    double bp,bn;
double convto,convfrom;
String cf,ct,ca;
Spinner from,to;
EditText camount;
    FloatingActionButton convert;
TextView output;
    String url;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection2;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency);



        tv_check_connection2=(TextView) findViewById(R.id.tv_check_connection2);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();


      from=(Spinner)findViewById(R.id.cur_from);
      from.setSelection(34,false);
      to=(Spinner)findViewById(R.id.cur_to);
      to.setSelection(25,false);
      camount=(EditText)findViewById(R.id.cur_amount);
      convert=(FloatingActionButton)findViewById(R.id.btn_convert);

      output=(TextView)findViewById(R.id.tv_output);



        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cf=from.getSelectedItem().toString();
                ct=to.getSelectedItem().toString();
                ca=camount.getText().toString();

        if(ca.equals(""))
        {
            camount.setError("enter amount");

            }
            else
                {
                    if (isOnline(Currencycon.this))
                    {

                        con(cf,ct);
                    }else {

                        Snackbar.make(view, "Internet connection required", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(Currencycon.this, "Internet connection required", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });





    }




    public static void dialog3(boolean value){



        if(value){

            //Toast.makeText(this, "d3 if", Toast.LENGTH_SHORT).show();
            Log.e("ms", "d3 if !!! ");
            tv_check_connection2.setText("Connected...!!!");
            tv_check_connection2.setBackgroundColor(Color.GREEN);
            tv_check_connection2.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection2.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            Log.e("ms", "d3 else !!! ");
            //Toast.makeText(this, "d3 else", Toast.LENGTH_SHORT).show();
            tv_check_connection2.setVisibility(View.VISIBLE);
            tv_check_connection2.setText("Could not Connect to internet...!!!");
            tv_check_connection2.setBackgroundColor(Color.RED);
            tv_check_connection2.setTextColor(Color.WHITE);
        }
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Currencycon.this,Tools.class);
        startActivity(i);
       Currencycon.this.finish();
        overridePendingTransition(0, 0);

    }


    private void con(final String from, final String to)
    {

        //list2=new ArrayList<HashMap<String,String>>();
        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> task= new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                pd=new ProgressDialog(Currencycon.this);
                pd.setMessage("CONVERTING.please wait..");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("id",s));
                try {
                    HttpClient client= new DefaultHttpClient();
                    url="http://apilayer.net/api/live?access_key=3f78d6fd31c85450724520a404e48a5b&currencies="+from+","+to+"&format=1";
                    //  HttpPost post=new HttpPost("http://apilayer.net/api/live?access_key=3f78d6fd31c85450724520a404e48a5b&currencies=AUD,INR&format=1");
                    HttpPost post=new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse response=client.execute(post);
                    HttpEntity entity=response.getEntity();
                    // entity.getContent();

                    JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
                    convfrom=exchangeRates.getJSONObject("quotes").getDouble("USD"+cf);
                    convto=exchangeRates.getJSONObject("quotes").getDouble("USD"+ct);
                    // System.out.println("1 " + exchangeRates.getString("source") + " in GBP : " + exchangeRates.getJSONObject("quotes").getDouble("USDINR") );

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(pd.isShowing())
                {
                    pd.dismiss();
                }

                //  Toast.makeText(Currencycon.this,""+convfrom+":"+convto, Toast.LENGTH_LONG).show();
              convvalue=(convto/convfrom)*Double.valueOf(ca);
                //Toast.makeText(Currencycon.this, ""+convvalue, Toast.LENGTH_SHORT).show();
                String op=""+ca+" "+cf+" = "+convvalue+" "+ct;
                output.setText(op);

            }
        };
        task.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curr, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.curaddinc:
            //add the function to perform here

            Intent intent=new Intent(Currencycon.this,Income.class);
            String result="";
            if(convvalue!=0.0)
            {
               result= String.format("%.0f",convvalue);
            }
            else
            {
                result="";
            }

            intent.putExtra("result",result);
            startActivity(intent);
            Currencycon.this.finish();
            overridePendingTransition(0, 0);
            return(true);
        case R.id.curaddexp:
            //add the function to perform here

            Intent intente=new Intent(Currencycon.this,Expense.class);
            String resulte="";
            if(convvalue!=0.0)
            {
                resulte = String.format("%.0f", convvalue);
            }
            else
            {
                result="";
            }
            intente.putExtra("result",resulte);
            startActivity(intente);
            Currencycon.this.finish();
            overridePendingTransition(0, 0);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }

}
