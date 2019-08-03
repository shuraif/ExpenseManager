package com.example.ms.expensemanager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.example.ms.expensemanager.DatabaseHelper.TABLE_NAME3;

public class Income extends AppCompatActivity {
    EditText amount,payer,des,ref,date1;
    Spinner cat;
   // Button add;
    DatabaseHelper dbh;
    Calendar myCalendar;
    int money;
    FloatingActionButton fab;
    String ssid=null ,ssincome=null,sspayer=null,sscat=null,ssdes=null,ssref=null,ssdate=null,ssimg,uid;
    int intinc;
    ImageView reciept;
    Bitmap bitmap;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income);


        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);
      //  Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

       fab = findViewById(R.id.fab);



        myCalendar = Calendar.getInstance();

        dbh=new DatabaseHelper(this);
        amount=(EditText)findViewById(R.id.et_iamount);
        payer=(EditText)findViewById(R.id.et_payer);
        cat=(Spinner)findViewById(R.id.spinner_cat);
        des=(EditText)findViewById(R.id.et_idescription);
        ref=(EditText)findViewById(R.id.et_ireference);
        date1=(EditText)findViewById(R.id.et_idate);
        //add=(Button)findViewById(R.id.btn_addincome);
        reciept=(ImageView)findViewById(R.id.inc_add_reciept);

        //getting data for editing
        Intent intent = getIntent();

        ssincome= intent.getStringExtra("income");
        sspayer= intent.getStringExtra("payer");
        ssid= intent.getStringExtra("id");
        sscat= intent.getStringExtra("cat");
        ssdes= intent.getStringExtra("des");
        ssref= intent.getStringExtra("ref");
        ssdate= intent.getStringExtra("sdate");


        amount.setText(ssincome);
        payer.setText(sspayer);
        des.setText(ssdes);
        ref.setText(ssref);
        date1.setText(ssdate);



        File f = new File("/mnt/sdcard/ExpManager/"+ssid+"inc.jpeg");
        //Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+ File.separator +inc_id+".jpeg");
        //  imgrec.setImageBitmap(bmp);
        if(f.exists())
        {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            reciept.setImageBitmap(bmp);
            Toast.makeText(this, "changing background", Toast.LENGTH_SHORT).show();
            reciept.setBackgroundResource(android.R.color.transparent);
        }






        String result=intent.getStringExtra("result");
        if(intent.getStringExtra("result")!=null)
        {
            amount.setText(result);
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
                new DatePickerDialog(Income.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sa=amount.getText().toString();
                String spay=payer.getText().toString();
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


                    int samount=Integer.valueOf(sa);



                    //inserting new income
                    //checking it is an update or new entry
                    Intent myIntent = getIntent(); // gets the previously created intent
                    String edit = myIntent.getStringExtra("edit");
                    boolean a;
                    if(myIntent.getStringExtra("edit")==null)
                    {
                        a= dbh.insertData(samount,spay,scat,sdes,sref,sdate,uid);
                        money=samount;
                        if (bitmap!=null)
                        {
                            uploadBitmap(bitmap);
                        }



                    }
                    else
                    {
                        intinc=Integer.valueOf(ssincome);
                        money=samount-intinc;
                        //Toast.makeText(Income.this, "updating income", Toast.LENGTH_SHORT).show();
                        a = dbh.updatIncome(ssid,sa,spay,scat,sdes,sref,sdate);
                        if (bitmap!=null)
                        {
                            uploadBitmap(bitmap);
                        }

                    }

                   //after inserting new income the balance need to be changed
                    SQLiteDatabase db = dbh.getWritableDatabase();
                    Cursor res = db.rawQuery("select * from balance_table where uid="+uid,null);
                    if(res.getCount() == 0)
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("bal",samount);
                        contentValues.put("uid",uid);
                        db.insert("balance_table",null ,contentValues);
                    }
                    else
                        {

                        res.moveToNext();

                             String cbal=res.getString(0);
                             int old_bal=Integer.valueOf(cbal);

                             int new_bal=old_bal+money;
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(" bal",new_bal);
                            contentValues.put("uid",uid);
                           /* db.insert("balance_table",null ,contentValues);*/
                            db.update("balance_table", contentValues, "UID = ?",new String[] { uid });
                        }








                   if (a)
                   {
                      /* Snackbar.make(view, "Income Added", Snackbar.LENGTH_LONG)
                               .setAction("Action", null).show();*/
                     //  Toast.makeText(Income.this, "inserted", Toast.LENGTH_SHORT).show();
                       Intent i=new Intent(Income.this,Home.class);
                       startActivity(i);
                       Income.this.finish();
                       overridePendingTransition(0, 0);
                   }else {
                      // Toast.makeText(Income.this, "not inserted", Toast.LENGTH_SHORT).show();
                       Snackbar.make(view, "Income not Added", Snackbar.LENGTH_LONG)
                               .setAction("Action", null).show();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && ContextCompat.checkSelfPermission(Income.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }



                ActivityCompat.requestPermissions(Income.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

            }
        });





    }

    private void uploadBitmap(final Bitmap bitmap) {

        //fst find file name of new image
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor maxid=db.rawQuery("select max(id) from income_table",null);
        maxid.moveToNext();
        String nextname=maxid.getString(0);




        String folder_main = "ExpManager";

        File dir = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file=new File(dir,nextname+"inc.jpeg");
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

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
           bitmap = MediaStore.Images.Media.getBitmap(Income.this.getContentResolver(), imageUri);

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
    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date1.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Income.this,Home.class);
        startActivity(i);
        Income.this.finish();
        overridePendingTransition(0, 0);
    }
}
