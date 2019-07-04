package com.example.movienightplanner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperMovies extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "nightPlannerMovies.db";
    public static final int DB_VERSION = 1;

    public DBHelperMovies(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieTable.SQL_DELETE);
        onCreate(db);
    }
}
