package com.example.denis.popularmovies.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.denis.popularmovies.Database.AppDatabase;
import com.example.denis.popularmovies.Models.Movie;

import java.util.List;

/**
 * Created by denis on 26/09/18.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<Movie>> movies;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the movies from the DataBase");
        movies = appDatabase.MovieDao().loadAllMovies();

    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
