package com.example.denis.popularmovies.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by denis on 21/09/18.
 */
@Entity(tableName = "movie")
public class Movie {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String poster_url;
    @SerializedName("overview")
    private String plot_synopsis;
    @SerializedName("vote_average")
    private String user_rating;
    @SerializedName("release_date")
    private String release_date;

    public Movie(int id, String title, String poster_url, String plot_synopsis, String user_rating,
                 String release_date) {
        this.id = id;
        this.title = title;
        this.poster_url = poster_url;
        this.plot_synopsis = plot_synopsis;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        String url = "http://image.tmdb.org/t/p/w342"+poster_url;
        this.poster_url = url;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
