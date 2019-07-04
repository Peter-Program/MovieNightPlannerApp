package com.example.movienightplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.movienightplanner.Models.Movie;
import com.example.movienightplanner.Models.SingletonMovieListModel;

public class DataSourceMovies {
    private String TAG = "dataBase";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSourceMovies(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelperMovies(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    //
    public Movie createItem(Movie item) {
        ContentValues values = item.toValues();
        mDatabase.insert(MovieTable.TABLE_MOVIES, null, values);
        return item;
    }

//    public void seedDataBase() {
//        for (Movie movie: SingletonMovieListModel.getInstance().getMoviesArray()) {
//            // putting event into the database
//            try {
//                Log.i(TAG, "adding movie to Database: " + movie.getTitle());
//                createItem(movie);
//            } catch (SQLException e) {
//                // Goes here if the id of the event matches a primary id of something already in the table
//                e.printStackTrace();
//            }
//        }
//    }

    public void saveDataToDataBase() {
        mDbHelper.onUpgrade(mDatabase, 1, 2);
        for (Movie movie: SingletonMovieListModel.getInstance().getMoviesArrayList()) {
            // putting event into the database
            try {
                Log.i(TAG, "adding movie to Database: " + movie.getTitle());
                createItem(movie);
            } catch (SQLException e) {
                // Goes here if the id of the event matches a primary id of something already in the table
                Log.i(TAG, "Movie already saved in database");
//                e.printStackTrace();
            }
        }
    }

    public void loadDataFromDataBase() {
        Cursor cursor = mDatabase.query(MovieTable.TABLE_MOVIES, MovieTable.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_ID)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_TITLE)));
            movie.setYear(cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_YEAR)));
            movie.setPoster(cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_POSTER)));

            // Attendees
            // add event to list
            Log.i(TAG, "Loading Event " + movie.getTitle() + " from DB");
            SingletonMovieListModel.getInstance().addMovie(movie);
        }
    }
}
