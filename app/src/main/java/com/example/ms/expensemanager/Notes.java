package com.example.ms.expensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notes extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listView;
    TextView textView;
    List<HashMap<String,String>> lis;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        myDb = new DatabaseHelper(this);


        preferences=getSharedPreferences("pref",0);
        editor=preferences.edit();
        editor.apply();

        uid=preferences.getString("uid",null);


        textView=(TextView)findViewById(R.id.empty_notes);
        listView = (ListView) findViewById(R.id.lst_notes);
        lis = new ArrayList<HashMap<String, String>>();
        fetchdata();

        SimpleAdapter adapter = new SimpleAdapter(Notes.this, lis, R.layout.list_notes, new String[]{"note","date","id"},new int[]{R.id.notes,R.id.dd,R.id.id});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView tv_id = (TextView) view.findViewById(R.id.id);
                String idtext=tv_id.getText().toString();

                TextView tv_text = (TextView) view.findViewById(R.id.notes);
                String text=tv_text.getText().toString();

                Intent intent=new Intent(Notes.this,NoteView.class);
                intent.putExtra("id",idtext);

                startActivity(intent);
                Notes.this.finish();;
                overridePendingTransition(0, 0);



            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here
            Intent intent=new Intent(Notes.this,New_note.class);
            intent.putExtra("note","");
            intent.putExtra("edit","false");
            startActivity(intent);
            Notes.this.finish();
            overridePendingTransition(0, 0);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Notes.this,Tools.class);
        startActivity(i);
      Notes.this.finish();
        overridePendingTransition(0, 0);
    }

    private void fetchdata()
    {
        Cursor res = myDb.getNotes(uid);
        if(res.getCount() == 0) {
            // show message
            textView.setVisibility(View.VISIBLE);

        }
        else {
            textView.setVisibility(View.INVISIBLE);
            while (res.moveToNext()) {

                HashMap<String,String> map=new HashMap<String, String>();
                map.put("id",res.getString(0));
                String nooot=res.getString(1);
                nooot=nooot.substring(0, Math.min(nooot.length(),75));
                map.put("note",nooot);
                map.put("date",res.getString(2));

                lis.add(map);
            }
        }


    }

}
