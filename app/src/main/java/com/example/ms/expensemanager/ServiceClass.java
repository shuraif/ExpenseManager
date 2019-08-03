package com.example.ms.expensemanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceClass extends Service {
    Runnable runnableCode;
    DatabaseHelper dbh;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String uid;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
       // Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
       // Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        DateFormat tf = new SimpleDateFormat("HH:m");
        String time = tf.format(Calendar.getInstance().getTime());
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String date = df.format(Calendar.getInstance().getTime());



        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);

       // Toast.makeText(this, "inside on start", Toast.LENGTH_SHORT).show();
     /*  Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();*/
        dbh=new DatabaseHelper(this);
        SQLiteDatabase db2 = dbh.getWritableDatabase();
        Cursor res = db2.rawQuery("select * from reminder_table where uid='"+uid+"'",null);

        while(res.moveToNext())
        {
         // Toast.makeText(this, "inside while", Toast.LENGTH_SHORT).show();
            String id=res.getString(0);
            String rdate=res.getString(1);
            String rtime=res.getString(2);
            String rpay=res.getString(3);
            String ramount=res.getString(4);
            String type=res.getString(7);
            String user;
          /*  Toast.makeText(this, rdate, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, rtime, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, rdate+"="+date+","+rtime+"="+time, Toast.LENGTH_LONG).show();*/

            if(rdate.equals(date)&&rtime.equals(time))
            {

                if(type.equals("Credit"))
                {
                    // Toast.makeText(this, "inside if", Toast.LENGTH_SHORT).show();
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.icon)
                                    .setContentTitle("Alert.....!!!")
                                    .setContentText("time to CREDIT "+ramount+" to "+rpay)
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                    Intent notificationIntent = new Intent(this, Home.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
    /*    overridePendingTransition(0, 0);
        finish();*/
                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(Integer.valueOf(id), builder.build());
                }
                else
                {

                    // Toast.makeText(this, "inside if", Toast.LENGTH_SHORT).show();
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.icon)
                                    .setContentTitle("Alert.....!!!")
                                    .setContentText("time to DEBIT "+ramount+" from "+rpay)
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                    Intent notificationIntent = new Intent(this, Home.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
    /*    overridePendingTransition(0, 0);
        finish();*/
                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(Integer.valueOf(id), builder.build());
                }








            }

        }




    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }
   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Toast.makeText(ServiceClass.this, "toast", Toast.LENGTH_LONG).show();
            }
        }, 300000);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }*/
}
