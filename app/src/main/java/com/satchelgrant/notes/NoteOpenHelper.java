package com.satchelgrant.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by satchelgrant on 6/2/17.
 */

public class NoteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "NoteDatabase";
    public static final String DATABASE_TABLE_NAME = "Content";
    private static final String NOTE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME +
            " (id serial PRIMARY KEY, title varchar, content text);";

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(NOTE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
