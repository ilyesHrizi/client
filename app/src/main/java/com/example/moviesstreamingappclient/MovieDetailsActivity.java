package com.example.moviesstreamingappclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesstreamingappclient.Adapter.MovieShowAdapter;
import com.example.moviesstreamingappclient.Model.GetVideoDetails;
import com.example.moviesstreamingappclient.Model.MoviesItemClickListenerNew;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements MoviesItemClickListenerNew {
    private ImageView MoviesThumbNail , MoviesCoverImg ;
    TextView tv_title , tv_description ;
    FloatingActionButton play_fab ;
    RecyclerView RvCast , recyclerView_similarMovies ;
    MovieShowAdapter movieShowAdapter ;
    DatabaseReference mDatabasereference ;
    List<GetVideoDetails> uploads , actionmovies ,sportMovies , comedyMovies ,romanticMovies , advantureMovies ;
    String current_video_url ;
    String current_video_category ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        inView();
        similarMoviesRecycler();
        similarMovies();
        play_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this,MoviePlayerActivty.class);
                intent.putExtra("videoUri",current_video_url);
                startActivity(intent);
            }
        });

    }

    private void similarMovies() {
        if (current_video_category.equals("Action")){
            movieShowAdapter = new MovieShowAdapter(this,actionmovies,this);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();

        }
        if (current_video_category.equals("Sports")){
            movieShowAdapter = new MovieShowAdapter(this,sportMovies,this);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();

        }
        if (current_video_category.equals("Adventure")){
            movieShowAdapter = new MovieShowAdapter(this,advantureMovies,this);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();

        } if (current_video_category.equals("Comedy")){
            movieShowAdapter = new MovieShowAdapter(this,comedyMovies,this);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();

        }
        if (current_video_category.equals("Romantic")){
            movieShowAdapter = new MovieShowAdapter(this,romanticMovies,this);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();

        }

    }

    private void similarMoviesRecycler() {
        uploads = new ArrayList<>();
        sportMovies = new ArrayList<>();
        comedyMovies = new ArrayList<>();
        romanticMovies = new ArrayList<>();
        advantureMovies = new ArrayList<>();
        actionmovies = new ArrayList<>();
        mDatabasereference = FirebaseDatabase.getInstance().getReference("videos");
        mDatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot:dataSnapshot.getChildren()){
                    GetVideoDetails upload = postsnapshot.getValue(GetVideoDetails.class);
                    if (upload.getVideo_category().equals("Action")){
                        actionmovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Sports")){
                       sportMovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Adventure")){
                        advantureMovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Comedy")){
                        comedyMovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Romantic")){
                        romanticMovies.add(upload);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inView() {
        play_fab = findViewById(R.id.play_fab);
        tv_title = findViewById(R.id.detail_movie_title);
        tv_description = findViewById(R.id.details_movies_desc);
        MoviesThumbNail =findViewById(R.id.details_movies_img);
        MoviesCoverImg = findViewById(R.id.detail_movies_cover);
        recyclerView_similarMovies = findViewById(R.id.recycler_similar_movies);
        String moviesTitle =getIntent().getExtras().getString("title");
        String imgRecoresId =getIntent().getExtras().getString("imgURL");
        String imageCover =getIntent().getExtras().getString("imgCover");
        String moviesUrl =getIntent().getExtras().getString("moviesUrl");
        String moviesDetailsText =getIntent().getExtras().getString("movieDetails");
        String moviesCategory =getIntent().getExtras().getString("movieCategory");
        current_video_url = moviesUrl ;
        current_video_category =moviesCategory ;
        Glide.with(this).load(imgRecoresId).into(MoviesThumbNail);
        Glide.with(this).load(imageCover).into(MoviesCoverImg);
        tv_title.setText(moviesDetailsText);
        tv_description.setText(moviesDetailsText);
        getSupportActionBar().setTitle(moviesTitle);


    }

    @Override
    public void onMovieClick(GetVideoDetails movie, ImageView imageView) {
        tv_title.setText(movie.getVideo_name());
        getSupportActionBar().setTitle(movie.getVideo_name());
        Glide.with(this).load(movie.getVideo_thumb()).into(MoviesThumbNail);
        Glide.with(this).load(movie.getVideo_thumb()).into(MoviesCoverImg);
        tv_description.setText(movie.getVideo_description());
        current_video_url = movie.getVideo_url();
        current_video_category = movie.getVideo_category();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailsActivity.this,imageView,"sharedName");
        options.toBundle();



    }
}