package com.example.movienightplanner.Database;

public class MovieTable {

    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_ID = "movieID";
    public static final String COLUMN_TITLE = "movieTitle";
    public static final String COLUMN_YEAR = "movieYear";
    public static final String COLUMN_POSTER = "moviePoster";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_TITLE,
                COLUMN_YEAR,
                COLUMN_POSTER};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_MOVIES + " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_YEAR + " TEXT," +
                    COLUMN_POSTER + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_MOVIES;
}
