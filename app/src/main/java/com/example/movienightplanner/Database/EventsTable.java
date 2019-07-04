package com.example.movienightplanner.Database;

public class EventsTable {


    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "eventsID";
    public static final String COLUMN_TITLE = "eventTitle";
    public static final String COLUMN_START_DATE = "eventStartDate";
    public static final String COLUMN_END_DATE = "eventEndDate";
    public static final String COLUMN_VENUE = "eventVenue";
    public static final String COLUMN_LOCATION = "eventLocation";
    public static final String COLUMN_MOVIE_ID = "eventMovie";
    public static final String COLUMN_ATTENDEES = "eventAttendees";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_START_DATE,
                COLUMN_END_DATE,
                COLUMN_VENUE,
                COLUMN_LOCATION,
                COLUMN_MOVIE_ID,
                COLUMN_ATTENDEES};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_START_DATE + " TEXT," +
                    COLUMN_END_DATE + " TEXT," +
                    COLUMN_VENUE + " TEXT," +
                    COLUMN_LOCATION + " TEXT," +
                    COLUMN_MOVIE_ID + " TEXT," +
                    COLUMN_ATTENDEES + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_EVENTS;
}
