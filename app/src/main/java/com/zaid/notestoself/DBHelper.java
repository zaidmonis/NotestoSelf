package com.zaid.notestoself;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zaid on 25/9/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, "stu", null, 3, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase sqLiteDatabase) {
                Log.d("----","DB OPen error");
            }
        });
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, note TEXT);");
    }

    public void addNote(String title, String note, SQLiteDatabase db){
        db.execSQL("insert into Notes values(null,\"" +title +"\",\"" + note + "\");");
    }
    public void updateNote(String title, String note, SQLiteDatabase db){
        db.execSQL("UPDATE Notes SET note = \"" +note +"\" WHERE title = \"" +title +"\" ;");
    }
    public void deleteNote(String title, SQLiteDatabase db){
        db.execSQL("DELETE FROM Notes WHERE title = \"" +title +"\" ;");
        //Toast.makeText(context, "DELETE FROM Notes WHERE title = \"" + title + "\" ;", Toast.LENGTH_SHORT).show();
    }

    public Cursor getTitles(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select title from Notes;", null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
