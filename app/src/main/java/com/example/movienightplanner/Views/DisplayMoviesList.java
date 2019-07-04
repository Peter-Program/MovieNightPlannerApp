package com.example.movienightplanner.Views;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.movienightplanner.BaseActivity;
import com.example.movienightplanner.Models.SingletonMovieListModel;
import com.example.movienightplanner.R;
import com.example.movienightplanner.RecyclerAdapterMovie;

public class DisplayMoviesList extends BaseActivity {

    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterMovie adapter;

    SingletonMovieListModel movieListModel = SingletonMovieListModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies_list);

        recyclerView = findViewById(R.id.recyclerViewMovies);
        layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterMovie(movieListModel.getMoviesArrayList());

        recyclerView.setAdapter(adapter);
    }
}
