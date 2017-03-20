package com.example.ojasvini.notesdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ojasvini on 3/17/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "myfilters.db";
    static final int DB_VERSION = 3;

    public DatabaseOpenHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        FilterTable.onUpgrade(db,3,4);
        Log.i("on create","on create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilterTable.onUpgrade(db,oldVersion,newVersion);
    }
}

