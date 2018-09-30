package com.example.denis.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.denis.popularmovies.Models.Trailer;
import com.example.denis.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 24/09/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{
    private Context context;
    private ArrayList<Trailer> trailers;


    public TrailerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trailer, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailers.get(holder.getAdapterPosition());
        holder.trailerBt.setText(trailer.getName());
        holder.trailerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube:"+trailer.getKey()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers){
        if (this.trailers != null){
            this.trailers.addAll(trailers);
            notifyDataSetChanged();
        }else{
            this.trailers = (ArrayList<Trailer>) trailers;
            notifyDataSetChanged();
        }
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        Button trailerBt;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerBt = itemView.findViewById(R.id.trailer);
        }
    }
}
