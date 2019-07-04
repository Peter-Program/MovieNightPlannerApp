package com.example.movienightplanner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperEvents extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "nightPlannerEvents.db";
    public static final int DB_VERSION = 1;

    public DBHelperEvents(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventsTable.SQL_DELETE);
        onCreate(db);
    }
}
