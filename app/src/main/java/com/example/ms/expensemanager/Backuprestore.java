package com.example.ms.expensemanager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Backuprestore extends AppCompatActivity {
    Button bkp,rst;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;

    final String uploadFilePath = "/data/data/com.example.ms.expensemanager/databases/";
    final String uploadFileName = "Expense.db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backuprestore);
/*
        bkp=(Button)findViewById(R.id.btn_bkp);
        rst=(Button)findViewById(R.id.btn_rst);


        upLoadServerUri = "http://expensemgr.000webhostapp.com/UploadToServer.php";

//"/data/data/com.example.ms.expensemanager/databases/Expence.db";
        bkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog = ProgressDialog.show(Backuprestore.this, "", "Uploading file...", true);


    // new MaterialFilePicker().withActivity(Backuprestore.this).withRequestCode(10).start();


                uploadFile("/ExpManager/DataBase","Expense.db");


            }

        });*/

    }


    /**
     * Upload the specified file to the PHP server.
     *
     * @param filePath The path towards the file.
     * @param fileName The name of the file that will be saved on the server.
     */
    /*private void uploadFile(String filePath, String fileName) {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(filePath));
            byte[] data;
            try {
                data = IOUtils.toByteArray(inputStream);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://expensemgr.000webhostapp.com/UploadToServer.php");

                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), fileName);
                MultipartEntity multipartEntity = new MultipartEntity();
                multipartEntity.addPart("file", inputStreamBody);
                httpPost.setEntity(multipartEntity);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                // Handle response back from script.
                if(httpResponse != null) {

                } else { // Error, no response.

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }


*/



    @Override
    public void onBackPressed() {
        Intent i=new Intent(Backuprestore.this,Tools.class);
        startActivity(i);
        Backuprestore.this.finish();
        overridePendingTransition(0, 0);
    }



}

