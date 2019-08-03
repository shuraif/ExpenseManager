package com.example.ms.expensemanager;

import android.annotation.SuppressLint;
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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.ms.expensemanager.NetworkChangeReceiver.isOnline;


public class Signup extends AppCompatActivity {
    EditText input_name,input_passwd,input_cpasswd,input_email;
    TextView already;
    Button signup;
String m;
ProgressDialog pd;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tv_check_connection3=(TextView) findViewById(R.id.tv_check_connection3);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        signup=(Button)findViewById(R.id.btn_signup);
        input_name=(EditText)findViewById(R.id.input_name);
        input_passwd=(EditText)findViewById(R.id.input_password);
        input_cpasswd=(EditText)findViewById(R.id.input_cpassword);
        input_email=(EditText)findViewById(R.id.input_email);
        already=(TextView)findViewById(R.id.link_login);


        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
                Signup.this.finish();
                overridePendingTransition(0, 0);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,passwd,cpasswd;
                name=input_name.getText().toString();
                email=input_email.getText().toString();
                passwd=input_passwd.getText().toString();
                cpasswd=input_cpasswd.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String unamePattern="^[a-zA-Z]+(([a-zA-Z ])?[a-zA-Z]*)*$";

                if(name.equals("")){
                    input_name.setError("enter name");
                }


                else  if (((name.length()<5)||(name.length()>40)) || (!name.matches(unamePattern)))
                {

                    if (name.length()<5 || name.length()>40)
                    {
                        input_name.setError("Sorry, Your name must be between 6 and 40 characters long");
                    }
                    else
                    {
                        input_name.setError("This name is not valid.Are you sure that you've entered your name correctly?");
                    }


                }

               else if (email.equals("")){
                    input_email.setError("enter email");
                }

                // onClick of button perform this simplest code.
              else  if (!email.matches(emailPattern))
                {
                    input_email.setError("enter valid email");
                }

                else if(passwd.equals(""))
                {
                    input_passwd.setError("enter password");
                }



              else  if((passwd.length()<6)||(passwd.length()>40)){
                   // System.out.println("Not Valid");
                     input_passwd.setError("Sorry, Your password must be between 6 and 40 characters long..!");


                }

                else if(cpasswd.equals("")){
                    input_cpasswd.setError("repeat password");
                }
                else if(!cpasswd.equals(passwd)){
                    input_cpasswd.setError("password do not match");

                }
              else{
                    if(isOnline(Signup.this)) {
                        asyncPOST(name, email, passwd);
                    }
                    else
                    {
                        Snackbar.make(view, "Internet connection required", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(Signup.this, "No Network", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });
    }




    @Override
    public void onBackPressed() {
        Intent i=new Intent(Signup.this,Login.class);

        startActivity(i);
        Signup.this.finish();
        overridePendingTransition(0, 0);
    }



    private  void asyncPOST(final String name, final String emai, final String pass){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask= new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                pd=new ProgressDialog(Signup.this);
                pd.setMessage("please wait..");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                List<NameValuePair> list =new ArrayList<>();
                list.add(new BasicNameValuePair("name",name));
                list.add(new BasicNameValuePair("email",emai));
                list.add(new BasicNameValuePair("pass",pass));
                try {
                    HttpClient client=new DefaultHttpClient();
                    HttpPost post= new HttpPost("http://expensemgr.000webhostapp.com/reg.php");
                    post.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse response=client.execute(post);
                    HttpEntity entity=response.getEntity();
                    InputStream is=entity.getContent();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(isr);
                    StringBuilder builder=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        builder.append(line);
                    }
                    m=builder.toString();
                }catch (Exception e){
                    Toast.makeText(Signup.this, "exception", Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(pd.isShowing()) {
                    pd.dismiss();

                }
                if(m.equals("false"))
                {
                    input_email.setError("email already exist");
                    Toast.makeText(Signup.this,"email already exist", Toast.LENGTH_LONG).show();

                }
                else {
                     Toast.makeText(Signup.this,m, Toast.LENGTH_LONG).show();
                     Intent intent=new Intent(Signup.this,Login.class);
                     startActivity(intent);
                     Signup.this.finish();
                    overridePendingTransition(0, 0);
                }
            }
        };
        asyncTask.execute();
    }


    public static void dialog2(boolean value){


        if(value){
            tv_check_connection3.setText("We are back...!!!");
            tv_check_connection3.setBackgroundColor(Color.GREEN);
            tv_check_connection3.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
            tv_check_connection3.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            tv_check_connection3.setVisibility(View.VISIBLE);
            tv_check_connection3.setText("Could not Connect to internet...!!!");
            tv_check_connection3.setBackgroundColor(Color.RED);
            tv_check_connection3.setTextColor(Color.WHITE);
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
}
