package com.example.denis.popularmovies.Network;

import com.example.denis.popularmovies.Models.MovieResult;
import com.example.denis.popularmovies.Models.Review;
import com.example.denis.popularmovies.Models.ReviewResult;
import com.example.denis.popularmovies.Models.Trailer;
import com.example.denis.popularmovies.Models.TrailerResult;


import java.util.List;

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

    @GET("3/movie/{id}/videos")
    Call<TrailerResult> getTrailers(
            @Path("id") String id,
            @Query("api_key") String api_key
    );

    @GET("3/movie/{id}/reviews")
    Call<ReviewResult> getReviews(
            @Path("id") String id,
            @Query("api_key") String api_key
    );
}
