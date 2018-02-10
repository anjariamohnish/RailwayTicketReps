package com.example.mohnish.railwayticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/02/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FileManager";
    private static final String TABLE_FILEDATA = "FileData";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FILEDATA + "(" + KEY_NAME + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FILEDATA);

        onCreate(sqLiteDatabase);
    }

    void AddFile(String filename) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, filename);
        db.insert(TABLE_FILEDATA, null, values);
        db.close();

    }

    void DelFile(String filename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILEDATA, KEY_NAME + " = ?",
                new String[]{String.valueOf(filename)});
        db.close();
    }

    List<String> GetFile() {
        List<String> files = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FILEDATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                files.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        return files;
    }
}
