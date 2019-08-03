package com.example.ms.expensemanager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.ms.expensemanager.DatabaseHelper.TABLE_NAME5;

public class Expense extends AppCompatActivity {
    EditText amount,payee,des,ref,date1;
    Spinner cat;

    FloatingActionButton fab;
    DatabaseHelper dbh;
    Calendar myCalendar;
    String ssid=null ,ssincome=null,sspayee=null,sscat=null,ssdes=null,ssref=null,ssdate=null,ssimg,uid;
    int money;
    int intinc;
    Bitmap bitmap;
    ImageView reciept;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);



        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);





        myCalendar = Calendar.getInstance();
        payee=(EditText)findViewById(R.id.et_payee);
        cat=(Spinner)findViewById(R.id.spinner_ecat);
        des=(EditText)findViewById(R.id.et_edescription);
        ref=(EditText)findViewById(R.id.et_ereference);
        date1=(EditText)findViewById(R.id.et_edate);
        amount=(EditText)findViewById(R.id.et_eamount);
        reciept=(ImageView)findViewById(R.id.exp_add_reciept);
        fab = findViewById(R.id.btn_addexpense);





        Intent intent = getIntent();
        ssincome= intent.getStringExtra("expense");
        sspayee= intent.getStringExtra("payee");
        ssid= intent.getStringExtra("id");
        sscat= intent.getStringExtra("cat");
        ssdes= intent.getStringExtra("des");
        ssref= intent.getStringExtra("ref");
        ssdate= intent.getStringExtra("sdate");

        amount.setText(ssincome);
        payee.setText(sspayee);
        des.setText(ssdes);
        ref.setText(ssref);
        date1.setText(ssdate);


        File f = new File("/mnt/sdcard/ExpManager/"+ssid+"exp.jpeg");
        //Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+ File.separator +inc_id+".jpeg");
        //  imgrec.setImageBitmap(bmp);
        if(f.exists())
        {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            reciept.setImageBitmap(bmp);
           reciept.setBackgroundResource(android.R.color.transparent);
        }



String result=intent.getStringExtra("result");
if(intent.getStringExtra("result")!=null)
{
    amount.setText(result);
}



String c=sscat;

        //spinner.setSelection ( spinner_array_list.indexOf(string) );



if(c!=null) {


    if (c.equals("All")) {
        cat.setSelection(0);
    } else if (c.equals("Automobile")) {
        cat.setSelection(1);
    } else if (c.equals("Entertinement")) {
        cat.setSelection(2);
    } else if (c.equals("Family")) {
        cat.setSelection(3);
    } else if (c.equals("Food")) {
        cat.setSelection(4);
    } else if (c.equals("Helth Care")) {
        cat.setSelection(5);
    } else if (c.equals("Home")) {
        cat.setSelection(6);
    } else if (c.equals("Ofice")) {
        cat.setSelection(7);
    } else if (c.equals("Loan")) {
        cat.setSelection(8);
    } else if (c.equals("Travel")) {
        cat.setSelection(9);
    } else if (c.equals("Tax")) {
        cat.setSelection(10);
    } else if (c.equals("Insurance")) {
        cat.setSelection(11);
    }

}

        //create datepicker dialogue
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        //action for date click
        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Expense.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dbh=new DatabaseHelper(this);


        amount=(EditText)findViewById(R.id.et_eamount);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sa=amount.getText().toString();
                String spay=payee.getText().toString();
                String sdes=des.getText().toString();
                String scat=cat.getSelectedItem().toString();
                String sref=ref.getText().toString();
                String sdate=date1.getText().toString();
                if(sa.equals("")){
                    amount.setError("please fill");
                }
                else if(sdate.equals("")){
                    date1.setError("please fill");
                }

                else
                {

            //inserting new expense
                    int samount=Integer.valueOf(sa);

                    Intent myIntent = getIntent(); // gets the previously created intent
                    String edit = myIntent.getStringExtra("edit");
                    boolean a;
                    if( myIntent.getStringExtra("edit")==null)
                    {
                        a = dbh.insertExpense(samount, spay, scat, sdes, sref,sdate,uid);
                        money=samount;
                        if (bitmap!=null)
                        {
                            uploadBitmap(bitmap);
                        }
                    }
                    else
                    {
                        a = dbh.updatExpense(ssid,sa, spay, scat, sdes, sref,sdate);
                        //ssincome old value\
                        //samoiunt new value
                        intinc=Integer.valueOf(ssincome);
                        money=samount-intinc;
                        if (bitmap!=null)
                        {
                            uploadBitmap(bitmap);
                        }

                    }

                    //after inserting new expnse the balance need to be changed
                    SQLiteDatabase db2 = dbh.getWritableDatabase();
                    Cursor res = db2.rawQuery("select * from balance_table where uid="+uid,null);
                    if(res.getCount() == 0)
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("bal",-samount);
                        contentValues.put("uid",uid);
                        db2.insert("balance_table",null ,contentValues);
                    }
                    else
                    {

                        res.moveToNext();

                        String cbal=res.getString(0);
                        int old_bal=Integer.valueOf(cbal);

                        int new_bal=old_bal-money;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(" bal",new_bal);
                        contentValues.put("uid",uid);
                        /* db.insert("balance_table",null ,contentValues);*/
                        db2.update("balance_table", contentValues, "UID = ?",new String[] { uid });
                    }//upto this updating balance after expense insertuon




               String hh="All";
                    SQLiteDatabase db = dbh.getWritableDatabase();


                  //Cursor bud_spend =db.rawQuery("select * from budget_table where category ='" + hh + "' and '" + sdate +"' >= date and '" + sdate + "' <= DATEEND  or category='" + scat + "' and '" + sdate +"' >= date and '" + sdate + "' <= DATEEND" , null);
//add category checking
                     Cursor bud_spend=db.rawQuery("select * from budget_table where   uid='"+uid+"' and category='All' or  uid='"+uid+"' and category='"+scat+"' ",null);





                    //while used for changing all balance to the category scat
                   // Toast.makeText(Expense.this, scat, Toast.LENGTH_SHORT).show();

                   while(bud_spend.moveToNext()) {
                      // Toast.makeText(Expense.this, "inside while", Toast.LENGTH_SHORT).show();

                      // Toast.makeText(Expense.this, sdate+">"+bud_spend.getString(5), Toast.LENGTH_LONG).show();
                     // Toast.makeText(Expense.this, sdate+">"+bud_spend.getString(5)+"  "+sdate+"<"+bud_spend.getString(7), Toast.LENGTH_LONG).show();
                      // int al=1;

                      String min=bud_spend.getString(5);
                      String max=bud_spend.getString(7);

                       Date dmin,dmax,dsel;

                       try {
                           dmin=new SimpleDateFormat("dd/MM/yyyy").parse(min);
                           dmax=new SimpleDateFormat("dd/MM/yyyy").parse(max);
                           dsel=new SimpleDateFormat("dd/MM/yyyy").parse(sdate);

                           //d.compareTo(min) >= 0 && d.compareTo(max) <= 0


                           if((dsel.compareTo(dmin)>=0)&&(dmax.compareTo(dsel)>=0))
                           {
                              // Toast.makeText(Expense.this, "inside if", Toast.LENGTH_SHORT).show();

                               String id=bud_spend.getString(0);
                               // Toast.makeText(Expense.this,bud_spend.getString(3), Toast.LENGTH_SHORT).show();
                               int al= Integer.valueOf(bud_spend.getString(4));
                               int spend = Integer.valueOf(bud_spend.getString(6));

                               int intsa = Integer.valueOf(sa);
                               spend = spend + intsa;

                               if(spend>=al){
                                   //methood for adding  notification
                                   addNotification();

                               }


                               String spen = String.valueOf(spend);
                               ContentValues contentValuess = new ContentValues();
                               contentValuess.put(" spend", spen);

                               db.update("budget_table", contentValuess, "ID = ?", new String[]{id});

                           }


                       } catch (ParseException e) {
                           e.printStackTrace();
                       }












                   }










                    if (a)
                    {

                        Intent i=new Intent(Expense.this,Home.class);
                        startActivity(i);
                      Expense.this.finish();
                        overridePendingTransition(0, 0);
                    }else {
                        Toast.makeText(Expense.this, "not inserted", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });







        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //checking the permission
                //if the permission is not given we will open setting to add permission
                //else app will not open
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(Income.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }*/



                ActivityCompat.requestPermissions(Expense.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

            }
        });








    }

    private void uploadBitmap(final Bitmap bitmap) {

        //fst find file name of new image
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor maxid=db.rawQuery("select max(id) from expense_table",null);
        maxid.moveToNext();
        String nextname=maxid.getString(0);




        String folder_main = "ExpManager";

        File dir = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file=new File(dir,nextname+"exp.jpeg");
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


//for geting image path after image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(Expense.this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                reciept.setImageBitmap(bitmap);
                reciept.setBackgroundResource(android.R.color.transparent);

                //calling the method uploadBitmap to upload image
                // uploadBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//update label of date after selection from  datepicker
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date1.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Expense.this,Home.class);
        startActivity(i);
        Expense.this.finish();
        overridePendingTransition(0, 0);
    }
    //methood for adding  notification
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Alert.....!!!")
                        .setContentText("Your expence exceeded the alert amount")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent notificationIntent = new Intent(this, Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        overridePendingTransition(0, 0);
        finish();
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
