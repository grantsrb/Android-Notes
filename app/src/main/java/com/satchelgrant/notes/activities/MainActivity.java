package com.satchelgrant.notes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.satchelgrant.notes.NoteOpenHelper;
import com.satchelgrant.notes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String NOTE_TITLE = "com.satchelgrant.notes.noteTitle";

    private ListView mListView;
    private List<String> mFilelist;
    private NoteOpenHelper mNoteHelper;
    private Float downX = Float.NaN;
    private Float downY = Float.NaN;
    private final Double DELTA = 50.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.notesList);
        mFilelist = new ArrayList<String>();

        mNoteHelper = new NoteOpenHelper(MainActivity.this);
        getdbContents();
    }

    public void newNote(View v){
        Intent intent = new Intent(this, NoteDisplayActivity.class);
        EditText editText = (EditText) findViewById(R.id.newNoteTitle);
        String title = editText.getText().toString().trim();
        if(!title.equals("")){
            intent.putExtra(this.NOTE_TITLE, title);
            startActivity(intent);
        } else{
            Toast toast = Toast.makeText(MainActivity.this, "Enter a Title!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void getdbContents(){
        SQLiteDatabase db = mNoteHelper.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + NoteOpenHelper.DATABASE_TABLE_NAME + ";";
        Cursor curs = db.rawQuery(sqlQuery, null);
        if(curs.moveToFirst()) {
            do {
                mFilelist.add(curs.getString(curs.getColumnIndex("title")));
            } while (curs.moveToNext());
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, mFilelist);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, NoteDisplayActivity.class);
                    String title = ((TextView) view).getText().toString();
                    intent.putExtra(NOTE_TITLE, title);
                    startActivity(intent);
                }
            });
        }
        db.close();
    }
}


