package com.satchelgrant.notes.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.satchelgrant.notes.NoteOpenHelper;
import com.satchelgrant.notes.R;
import com.satchelgrant.notes.models.Note;

public class NoteDisplayActivity extends AppCompatActivity {
    protected Note mNote;
    protected EditText mNoteText;
    protected NoteOpenHelper mNoteHelper;
    private Boolean mSaveTheNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        mNoteText = (EditText) findViewById(R.id.noteText);
        mSaveTheNote = true;

        Intent intent = getIntent();
        final String title = intent.getStringExtra(MainActivity.NOTE_TITLE);
        mNote = new Note(title);
        TextView noteTitle = (TextView) findViewById(R.id.noteTitle);
        noteTitle.setText(title);

        mNoteHelper = new NoteOpenHelper(NoteDisplayActivity.this);
        SQLiteDatabase db = mNoteHelper.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + NoteOpenHelper.DATABASE_TABLE_NAME +
                " WHERE title = '" + mNote.getTitle() +"';";
        Cursor curs = db.rawQuery(sqlQuery, null);
        if(curs.moveToFirst()){
            mNote.setId(curs.getLong(curs.getColumnIndex("id")));
            mNote.setContent(curs.getString(curs.getColumnIndex("content")));

            mNoteText.setText(mNote.getContent());
        }
        db.close();
    }

    public void saveNote(View v){
        Intent intent = new Intent(this, MainActivity.class);
        mSaveTheNote = true;
        save2db();
        mSaveTheNote = false;
        Toast toast = Toast.makeText(NoteDisplayActivity.this, "Saved", Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    public void deleteNote(View v){
        Intent intent = new Intent(NoteDisplayActivity.this, MainActivity.class);
        mSaveTheNote = false;

        SQLiteDatabase db = mNoteHelper.getReadableDatabase();
        String sqlStatement = "DELETE FROM " + NoteOpenHelper.DATABASE_TABLE_NAME +
                " WHERE title='" + mNote.getTitle() + "';";
        db.execSQL(sqlStatement);
        db.close();
        Toast toast = Toast.makeText(NoteDisplayActivity.this, "Deleted", Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mSaveTheNote && mNote.getId() != -1){
            save2db();
        }
    }

    private void save2db(){
        SQLiteDatabase db = mNoteHelper.getWritableDatabase();
        if (mNote.getId() == -1) {
            String sqlStatement = "INSERT INTO " + NoteOpenHelper.DATABASE_TABLE_NAME +
                    " (title, content) VALUES ('" + mNote.getTitle() + "','" + mNoteText.getText().toString() + "');";
            db.execSQL(sqlStatement);
        } else {
            ContentValues contentVals = new ContentValues();
            contentVals.put("title", mNote.getTitle());
            contentVals.put("content", mNoteText.getText().toString());
            db.update(NoteOpenHelper.DATABASE_TABLE_NAME, contentVals, "title='"+mNote.getTitle()+"'", null);
        }
        db.close();
    }

}
