package com.example.ms.expensemanager;



import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.ms.expensemanager.NetworkChangeReceiver.*;

import static com.example.ms.expensemanager.NetworkChangeReceiver.isOnline;//form network reciever class

public class Login extends AppCompatActivity{
    EditText uname,passwd;
    Button login;
    TextView signup;
    String m;
    ProgressDialog pd;//circle animation during login process
    SharedPreferences preferences;//shared preference are used for creating session in android
    SharedPreferences.Editor editor;//for editing shared preference
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;
    public static boolean s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        //code for using shared preference
        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        String uid=preferences.getString("uid",null);
        if(uid!=null)
        {
            Intent intent=new Intent(Login.this,Home.class);
            startActivity(intent);
            Login.this.finish();
            overridePendingTransition(0, 0);
        }

        uname=(EditText)findViewById(R.id.et_uname);
        passwd=(EditText)findViewById(R.id.et_pwd);
        login=(Button)findViewById(R.id.btn_login);
        signup=(TextView)findViewById(R.id.link_signup);
         login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String username=uname.getText().toString();
             String password=passwd.getText().toString();
             if(username.equals(""))
             {
                 uname.setError("please fill");
             }
            else if(password.equals(""))
             {
                 passwd.setError("please fill");
             }
             else
             {

                 if(isOnline(Login.this))
                 {
                     asyncPOST(username,password);
                 }else {
                     Snackbar.make(view, "Internet connection required", Snackbar.LENGTH_LONG)
                             .setAction("Action", null).show();
                     //Toast.makeText(Login.this, "No network", Toast.LENGTH_SHORT).show();
                 }

                 /* Intent intent=new Intent(Login.this,Home.class);
                 startActivity(intent);*/

             }

         }
     });
        signup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(Login.this,Signup.class);
             startActivity(intent);
             Login.this.finish();
             overridePendingTransition(0, 0);
         }
     });
    }

    private  void asyncPOST(final String id, final String pass){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask= new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                pd=new ProgressDialog(Login.this);
                pd.setMessage("please wait..");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                List<NameValuePair> list =new ArrayList<>();
                list.add(new BasicNameValuePair("id",id));
                list.add(new BasicNameValuePair("pass",pass));
                try {
                    HttpClient client=new DefaultHttpClient();
                    HttpPost post= new HttpPost("http://expensemgr.000webhostapp.com/login.php");
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
                    Toast.makeText(Login.this, "exception", Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(pd.isShowing()) {
                    pd.dismiss();

                }
                if(m.equals("false")){
                    uname.setError("account doesnt exist");

                }
                else {
                   // Toast.makeText(Login.this, m, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(Login.this,m, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);
                    Login.this.finish();
                    overridePendingTransition(0, 0);
                    editor.putString("uid",m);
                    editor.apply();

                }
            }
        };
        asyncTask.execute();
    }


    public static void dialog(boolean value){

        if(value){
            tv_check_connection.setText("We are back...!!!");
            tv_check_connection.setBackgroundColor(Color.GREEN);
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
            tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet...!!!");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
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
