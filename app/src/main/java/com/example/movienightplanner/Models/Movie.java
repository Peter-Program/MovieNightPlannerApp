package com.example.movienightplanner.Models;

import android.content.ContentValues;

import com.example.movienightplanner.Database.MovieTable;

import java.util.UUID;

public class Movie {
    String id; // random gen combo of Letters and Numbers, Unique
    String title; // title of the movie
    String year; // the year the movie was released
    String poster; // an image of the poster used to promote the movie

    public Movie(String id, String title, String year, String poster) {
        idConstructor(id);
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    public Movie() {

    }

    // inside DataItem (AKA movie)
    public ContentValues toValues() {
        ContentValues values = new ContentValues(4);

        values.put(MovieTable.COLUMN_ID, id);
        values.put(MovieTable.COLUMN_TITLE, title);
        values.put(MovieTable.COLUMN_YEAR, year);
        values.put(MovieTable.COLUMN_POSTER, poster);
        // do for each column

        return values;
    }

    private void idConstructor(String id) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
    }

    public String toDisplayString() {
        String s = title + ",\nReleased: " + year;
        return s;
    }

    public String getPosterPath() {
        String path = poster;
        return path;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
