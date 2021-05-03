package com.example.moviesstreamingappclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesstreamingappclient.Model.GetVideoDetails;
import com.example.moviesstreamingappclient.Model.MoviesItemClickListenerNew;
import com.example.moviesstreamingappclient.R;

import java.util.List;

public class MovieShowAdapter extends RecyclerView.Adapter<MovieShowAdapter.MyViewHolder>{
private Context mContext ;
private List<GetVideoDetails> uploads ;
MoviesItemClickListenerNew moviesItemClickListenerNew ;

    public MovieShowAdapter(Context mContext, List<GetVideoDetails> uploads, MoviesItemClickListenerNew moviesItemClickListenerNew) {

        this.mContext = mContext;
        this.uploads = uploads;
        this.moviesItemClickListenerNew = moviesItemClickListenerNew;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item_new , parent , false);

        return new  MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieShowAdapter.MyViewHolder holder, int position) {
        GetVideoDetails getVideoDetails = uploads.get(position);
        holder.tvTitle.setText(getVideoDetails.video_name);
        Glide.with(mContext).load(getVideoDetails.getVideo_thumb()).into(holder.ImgMovie);

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle ;
        ImageView ImgMovie ;
        ConstraintLayout container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_movies_title);
            ImgMovie = itemView.findViewById(R.id.item_movies_img);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviesItemClickListenerNew.onMovieClick(uploads.get(getAdapterPosition()),ImgMovie);
                }
            });
        }
    }
}
