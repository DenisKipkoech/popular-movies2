package com.example.denis.popularmovies.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denis on 21/09/18.
 */

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.themoviedb.org/";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
