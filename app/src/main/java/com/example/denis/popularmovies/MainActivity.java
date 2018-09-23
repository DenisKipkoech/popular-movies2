package com.example.denis.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.denis.popularmovies.Adapter.MoviesAdapter;
import com.example.denis.popularmovies.Models.Movie;
import com.example.denis.popularmovies.Models.MovieResult;
import com.example.denis.popularmovies.Network.ApiEndPointInterface;
import com.example.denis.popularmovies.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private String CATEGORY = "popular" ;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    private static final String API_KEY = "7bdc0adfb0ccb7eadb85e9fae2e84742";
    private static final int COLUMN_SPAN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiEndPointInterface mInterface = RetrofitClientInstance.getRetrofitInstance()
                                            .create(ApiEndPointInterface.class);

        Call<MovieResult> call = mInterface.getMovies(CATEGORY, API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult result = response.body();
                List<Movie> movies = result.getMovies();
                getMoviesData(movies);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getMoviesData(List<Movie> movies){
        recyclerView = findViewById(R.id.recycler);
        adapter = new MoviesAdapter(this, (ArrayList<Movie>) movies);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(MainActivity.this, COLUMN_SPAN);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}

