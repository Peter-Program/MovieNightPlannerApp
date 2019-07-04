package com.example.movienightplanner.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SingletonMovieListModel {
    private static final HashMap lst = new HashMap();
    private static SingletonMovieListModel myObj;

    // Get instance of class
    public static SingletonMovieListModel getInstance(){
        if(myObj == null){
            myObj = new SingletonMovieListModel();
        }
        return myObj;
    }

    public void addMovie(Movie movie) {
        if (lst.get(movie.getId()) == null) {
            lst.put(movie.getId(), movie);
            Log.i("dataBase", "put movie in system");
        } else
            Log.i("dataBase", "movie already in system");
//        movieList.add(movie);
    }

    public ArrayList<Movie> getMoviesArrayList() {
        Iterator<Movie> iterator = lst.values().iterator();
        ArrayList<Movie> arrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            Movie m = iterator.next();
            arrayList.add(m);
        }
        return arrayList;
    }

    public Movie getMovieOfID(String id) {
        return (Movie) lst.get(id);
    }

}
