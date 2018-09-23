package com.example.denis.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.denis.popularmovies.Adapter.PosterViewModel;
import com.example.denis.popularmovies.Models.Movie;
import com.example.denis.popularmovies.databinding.ActivityMovieDetailBinding;

public class MovieDetail extends AppCompatActivity {

    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if (intent != null) {
            movie = new Movie(
                    intent.getIntExtra(String.valueOf(R.string.movie_id), 0),
                    intent.getStringExtra(String.valueOf(R.string.intent_title)),
                    intent.getStringExtra(String.valueOf(R.string.intent_poster)),
                    intent.getStringExtra(String.valueOf(R.string.intent_plot)),
                    intent.getStringExtra(String.valueOf(R.string.intent_rating)),
                    intent.getStringExtra(String.valueOf(R.string.intent_release))
            );
            ActivityMovieDetailBinding binder = DataBindingUtil.setContentView(this,
                    R.layout.activity_movie_detail);
            binder.setMovie(movie);
            binder.setPosterViewModel(new PosterViewModel());

        }
    }
}
