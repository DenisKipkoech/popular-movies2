package com.example.denis.popularmovies.Network;

import com.example.denis.popularmovies.Models.MovieResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by denis on 21/09/18.
 */

public interface ApiEndPointInterface {
    @GET("3/movie/{category}")
    Call<MovieResult> getMovies(
            @Path("category") String category,
            @Query("api_key") String api_key
//            @Query("page") int page
    );
}
