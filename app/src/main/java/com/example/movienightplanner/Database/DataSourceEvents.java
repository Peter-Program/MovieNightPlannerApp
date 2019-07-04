package com.example.movienightplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.movienightplanner.Models.Event;
import com.example.movienightplanner.Models.SingletonEventsListModel;

public class DataSourceEvents {
    private String TAG = "dataBase";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSourceEvents(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelperEvents(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }



    //
    public Event createItem(Event item) {
        ContentValues values = item.toValues();
        mDatabase.insert(EventsTable.TABLE_EVENTS, null, values);
        return item;
    }

    public void saveDataToDataBase() {
        mDbHelper.onUpgrade(mDatabase, 1, 2);
        for (Event event: SingletonEventsListModel.getInstance().getSortedEventsArray()) {
            // putting event into the database
            try {
                Log.i(TAG, "adding event to Database: " + event.getTitle());
                createItem(event);
            } catch (SQLException e) {
                // Goes here if the id of the event matches a primary id of something already in the table
                Log.i(TAG, "Event already saved in database");
//                e.printStackTrace();
            }
        }
    }

    public void loadDataFromDataBase() {
        Cursor cursor = mDatabase.query(EventsTable.TABLE_EVENTS, EventsTable.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Event event = new Event();
            event.setId(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_ID)));
            event.setTitle(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_TITLE)));
            event.setVenue(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_VENUE)));
            event.setStartDate(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_START_DATE)));
            event.setEndDate(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_END_DATE)));
            event.setLocation(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_LOCATION)));
            event.setMovieFromId(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_MOVIE_ID)));
            // Attendees
            // add event to list
            Log.i(TAG, "Loading Event " + event.getTitle() + " from DB");
            SingletonEventsListModel.getInstance().addEvent(event);
        }
    }
}
