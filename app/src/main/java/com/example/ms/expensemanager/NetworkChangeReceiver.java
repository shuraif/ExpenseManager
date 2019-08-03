package com.example.ms.expensemanager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.example.ms.expensemanager.Currencycon.dialog3;
import static com.example.ms.expensemanager.Login.dialog;
import static com.example.ms.expensemanager.Login.s;
import static com.example.ms.expensemanager.Signup.dialog2;


//this class is used for checking internet conection.this class is registered as reciever in manifies .so it automatically executes
public class NetworkChangeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isOnline(context)) {
                Log.e("ms", "onrecieve called !!! ");
                dialog(true);
                dialog2(true);
                dialog3(true);

                Log.e("ms", "Online Connect Intenet ");
            }
            else {
                dialog(false);
                dialog2(false);
                dialog3(false);


                Log.e("ms", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static   boolean isOnline(Context context) {
        try {
            Log.e("ms", "isonilne called !!! ");
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}