package com.example.denis.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denis.popularmovies.Models.Review;
import com.example.denis.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 24/09/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private ArrayList<Review> reviews;
    private Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                        .inflate(R.layout.list_reviews, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        final Review review = reviews.get(holder.getAdapterPosition());

        holder.authorView.setText(review.getAuthor());
        holder.contentView.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView authorView, contentView;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            authorView = itemView.findViewById(R.id.authorView);
            contentView = itemView.findViewById(R.id.content);
        }
    }

    public void setReviews(List<Review> reviews){
        if (this.reviews != null){
            this.reviews.addAll(reviews);
            notifyDataSetChanged();
        }else{
            this.reviews =(ArrayList<Review>) reviews;
            notifyDataSetChanged();
        }
    }
}
