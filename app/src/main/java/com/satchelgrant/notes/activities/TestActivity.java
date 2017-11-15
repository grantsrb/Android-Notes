package com.satchelgrant.notes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.satchelgrant.notes.NoteOpenHelper;
import com.satchelgrant.notes.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.NOTE_TITLE);
        TextView text = (TextView) findViewById(R.id.testText);
        text.setText(title);

        new Thread(new Runnable() {
            @Override
            public void run() {
                NoteOpenHelper noteDB = new NoteOpenHelper(TestActivity.this);
                SQLiteDatabase db = noteDB.getReadableDatabase();
                String sqlStatement = "SELECT * FROM " + NoteOpenHelper.DATABASE_TABLE_NAME + ";";
                Cursor curs = db.rawQuery(sqlStatement, null);
                if(curs.moveToFirst()){
                    do{
                        Log.d("TEST", curs.getString(curs.getColumnIndex("title")));
                        Log.d("TEST", curs.getString(curs.getColumnIndex("content")));
                    } while(curs.moveToNext());
                }
                db.close();
            }
        }).start();
    }
}
