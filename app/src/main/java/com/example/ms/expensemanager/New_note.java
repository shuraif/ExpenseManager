package com.example.ms.expensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class New_note extends AppCompatActivity {
    EditText new_note;
    DatabaseHelper dbh;
    String edit,id,uid;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_notes);
        new_note=(EditText)findViewById(R.id.et_new_note);
        Intent myIntent = getIntent();
        final String note = myIntent.getStringExtra("note");
   edit =myIntent.getStringExtra("edit");
   id=myIntent.getStringExtra("id");
        new_note.setText(note);
        dbh=new DatabaseHelper(this);



        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.save:
            //add the function to perform here
            String note=new_note.getText().toString();
            if(note.equals("")){
               new_note.setError("please type something");
            }
            else
            {
                String dmy = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                if(edit.equals("false")) {



                    boolean a = dbh.insertnotes(note,dmy,uid);
                    if (a) {
                       // Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();
                    }
                    if (!a) {
                        Toast.makeText(this, "insert fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_note.this, Notes.class);
                    startActivity(intent);
                    New_note.this.finish();
                    overridePendingTransition(0, 0);
                }

                else{
                    boolean a = dbh.updatenotes(note,dmy,id);
                    if (a) {
                       // Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
                    }
                    if (!a) {
                        Toast.makeText(this, "update fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_note.this, Notes.class);
                    startActivity(intent);
                    New_note.this.finish();
                    overridePendingTransition(0, 0);

                }
            }
            return(true);
        case R.id.cancel:
            //add the function to perform here
            Intent intent=new Intent(New_note.this,Notes.class);
            startActivity(intent);
            New_note.this.finish();
            overridePendingTransition(0, 0);

            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(New_note.this,Notes.class);
        startActivity(i);
  New_note.this.finish();
        overridePendingTransition(0, 0);
    }


}
