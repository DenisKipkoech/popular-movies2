package com.example.denis.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.denis.popularmovies.Adapter.MoviesAdapter;
import com.example.denis.popularmovies.Models.Constants;
import com.example.denis.popularmovies.Models.Movie;
import com.example.denis.popularmovies.Models.MovieResult;
import com.example.denis.popularmovies.Models.Review;
import com.example.denis.popularmovies.Models.ReviewResult;
import com.example.denis.popularmovies.Models.Trailer;
import com.example.denis.popularmovies.Models.TrailerResult;
import com.example.denis.popularmovies.Network.ApiEndPointInterface;
import com.example.denis.popularmovies.Network.RetrofitClientInstance;
import com.example.denis.popularmovies.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private String CATEGORY = "popular";
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    private static final int COLUMN_SPAN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void makeApiCall(String category){
        ApiEndPointInterface mInterface = RetrofitClientInstance.getRetrofitInstance()
                .create(ApiEndPointInterface.class);

        Call<MovieResult> call = mInterface.getMovies(category, Constants.API_KEY);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular){
            CATEGORY = "popular";
            makeApiCall(CATEGORY);
        }
        if (id == R.id.action_top_rated){
            CATEGORY = "top_rated";
            makeApiCall(CATEGORY);
        }
        if (id == R.id.action_favourite){

        }
        return super.onOptionsItemSelected(item);
    }

    private void getFavouriteMovies(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.setMovies((ArrayList<Movie>) movies);
            }
        });

    }


}

