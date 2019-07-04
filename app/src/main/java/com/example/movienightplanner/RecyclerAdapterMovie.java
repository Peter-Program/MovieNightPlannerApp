package com.example.movienightplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movienightplanner.Models.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RecyclerAdapterMovie extends RecyclerView.Adapter<RecyclerAdapterMovie.MovieViewHolder> {

    static ArrayList<Movie> movies;

    public RecyclerAdapterMovie(ArrayList<Movie> m) {
        movies = m;
    }

    // Responsible for each object view
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item_recyclerview_layout, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie m = movies.get(i);
        String s = m.toDisplayString();
        movieViewHolder.getMovieTitle().setText(s);
        String posterPath = m.getPosterPath();
        try (InputStream stream =
                     movieViewHolder.getViewContext().getContext().getAssets().open(posterPath)) {
            Drawable d = Drawable.createFromStream(stream, null);
            movieViewHolder.getPosterImage().setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView movieTitle;
        private final ImageView posterImage;
        private final View viewContext;

        public MovieViewHolder(View v) {
            super(v);
            viewContext = v;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext().getString(R.string.movie_select));
                    intent.putExtra(v.getContext().getString(R.string.movie_id), movies.get(getAdapterPosition()).getId());
                    //LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                    ((Activity) v.getContext()).setResult(RESULT_OK, intent);
                    ((Activity) v.getContext()).finish();
                }
            });
            movieTitle = v.findViewById(R.id.movieTitle);
            posterImage = v.findViewById(R.id.moviePosterImage);
        }

        public TextView getMovieTitle() {
            return movieTitle;
        }

        public ImageView getPosterImage() {
            return posterImage;
        }

        public View getViewContext() {
            return viewContext;
        }
    }
}
