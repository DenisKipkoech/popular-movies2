package com.example.denis.popularmovies.Adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by denis on 23/09/18.
 */

public class PosterViewModel {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url){
        Picasso.with(imageView.getContext())
                .setLoggingEnabled(true);
        Picasso.with(imageView.getContext())
                .load("http://image.tmdb.org/t/p/w342"+url)
                .into(imageView);
    }
}
