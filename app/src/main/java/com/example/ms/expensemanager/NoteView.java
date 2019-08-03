package com.example.ms.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class NoteView extends AppCompatActivity {
  String text;
    TextView tv_note_view;
    DatabaseHelper myDb;
    String idp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteview);
        myDb = new DatabaseHelper(this);
        tv_note_view=(TextView)findViewById(R.id.tv_view_note);


//code for getting the id and find notes according to it
        Intent myIntent = getIntent(); // gets the previously created intent
       idp = myIntent.getStringExtra("id");
        Cursor res = myDb.getNotewithid(idp);

        res.moveToNext();
        text=res.getString(0);
        tv_note_view.setText(text);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.edit:
            //add the function to perform here
            Intent intent=new Intent(NoteView.this,New_note.class);
            intent.putExtra("note",text);
            intent.putExtra("edit","true");
            intent.putExtra("id",idp);
            startActivity(intent);

            NoteView.this.finish();
            overridePendingTransition(0, 0);
            return(true);
        case R.id.delete:
            DatabaseHelper delnot =new DatabaseHelper(this);
            String sid=String.valueOf(idp);
            delnot.deleteNote(sid);
            Intent iintent=new Intent(NoteView.this,Notes.class);
            startActivity(iintent);
            NoteView.this.finish();
            overridePendingTransition(0, 0);

    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(NoteView.this,Notes.class);
        startActivity(i);
        NoteView.this.finish();
        overridePendingTransition(0, 0);
    }

}
