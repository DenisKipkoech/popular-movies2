package com.example.denis.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.popularmovies.Adapter.ReviewAdapter;
import com.example.denis.popularmovies.Adapter.TrailerAdapter;
import com.example.denis.popularmovies.Database.AppDatabase;
import com.example.denis.popularmovies.Database.AppExecutors;
import com.example.denis.popularmovies.Models.Constants;
import com.example.denis.popularmovies.Models.Movie;
import com.example.denis.popularmovies.Models.Review;
import com.example.denis.popularmovies.Models.ReviewResult;
import com.example.denis.popularmovies.Models.Trailer;
import com.example.denis.popularmovies.Models.TrailerResult;
import com.example.denis.popularmovies.Network.ApiEndPointInterface;
import com.example.denis.popularmovies.Network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {
    private Movie movie;
    @BindView(R.id.title_view)
     TextView titleView;
    @BindView(R.id.release_date_view)
     TextView releaseView;
    @BindView(R.id.rating_view)
     TextView ratingView;
    @BindView(R.id.movie_synopsis_view)
     TextView contentView;
    @BindView(R.id.poster_view)
     ImageView poster;
    @BindView(R.id.trailer_recycler)
     RecyclerView trailerRecyclerView;
    @BindView(R.id.review_recycler)
     RecyclerView reviewRecyclerView;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private AppDatabase database;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (intent != null) {
            movie = new Movie(
                    intent.getIntExtra(String.valueOf(R.string.movie_id), 0),
                    intent.getStringExtra(String.valueOf(R.string.intent_title)),
                    intent.getStringExtra(String.valueOf(R.string.intent_poster)),
                    intent.getStringExtra(String.valueOf(R.string.intent_plot)),
                    intent.getStringExtra(String.valueOf(R.string.intent_rating)),
                    intent.getStringExtra(String.valueOf(R.string.intent_release))
            );

            Picasso.with(getApplicationContext()).setLoggingEnabled(true);
            Picasso.with(getApplicationContext())
                    .load("http://image.tmdb.org/t/p/w342" + movie.getPoster_url())
                    .into(poster);


            makeTrailerCall(String.valueOf(movie.getId()));
            makeReviewCall(String.valueOf(movie.getId()));
            populateUI(movie);

        }





    }


    private void makeTrailerCall(String id){
        ApiEndPointInterface mInterface = RetrofitClientInstance.getRetrofitInstance()
                .create(ApiEndPointInterface.class);
        Call<TrailerResult> call = mInterface.getTrailers(id, Constants.API_KEY);
        call.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                TrailerResult result = response.body();
                List<Trailer> trailers = result.getTrailers();
                getTrailers();
                trailerAdapter.setTrailers(trailers);
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void getTrailers(){
        trailerAdapter = new TrailerAdapter(this);
        trailerRecyclerView.setHasFixedSize(true);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        trailerRecyclerView.setAdapter(trailerAdapter);
    }

    public void makeReviewCall(String id){
        ApiEndPointInterface mInterface = RetrofitClientInstance.getRetrofitInstance()
                                        .create(ApiEndPointInterface.class);
        Call<ReviewResult> call = mInterface.getReviews(id, Constants.API_KEY);
        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                ReviewResult result = response.body();
                List<Review> reviews = result.getReviewList();
                getReviews();
                reviewAdapter.setReviews(reviews);
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });
    }

    private void getReviews(){
        reviewAdapter = new ReviewAdapter(this);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        reviewRecyclerView.setAdapter(reviewAdapter);

    }

    private void populateUI(Movie movie){
        titleView.setText(movie.getTitle());
        ratingView.setText(movie.getUser_rating()+"/10");
        releaseView.setText(movie.getRelease_date());
        contentView.setText(movie.getPlot_synopsis());
    }

    private void addMovieToFavourite(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.MovieDao().insertMovie(movie);

            }
        });
        Toast.makeText(getApplicationContext(), String.valueOf(R.string.movie_added),
                Toast.LENGTH_LONG).show();
    }

    private void removeMovieFromFavourite(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.MovieDao().deleteMovie(movie);
            }
        });
        Toast.makeText(getApplicationContext(), String.valueOf(R.string.movie_removed),
                Toast.LENGTH_LONG).show();
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if (checked){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(String.valueOf(movie.getId()), true);
            editor.commit();
            Toast.makeText(getBaseContext(), "toggle switched on", Toast.LENGTH_SHORT)
                    .show();
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(String.valueOf(movie.getId()), false);
            editor.commit();
            Toast.makeText(getBaseContext(), "toggle switched off", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
