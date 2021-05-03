package com.example.moviesstreamingappclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.moviesstreamingappclient.Adapter.MovieShowAdapter;
import com.example.moviesstreamingappclient.Adapter.SliderPagerAdapterNew;
import com.example.moviesstreamingappclient.Model.GetVideoDetails;
import com.example.moviesstreamingappclient.Model.MoviesItemClickListenerNew;
import com.example.moviesstreamingappclient.Model.SliderSide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MoviesItemClickListenerNew {

    MovieShowAdapter movieShowAdapter ;
    DatabaseReference mDatabaseReference;
    private List<GetVideoDetails> uploads ,uploadslistLatests, uploadslistPopular ;
    private List<GetVideoDetails> actionmovies ,sportmovies , comedymovies ,romanticmovies ,advanturemovies ;
    private ViewPager sliderPager ;
    private List<SliderSide> uploadsSlider ;
    private TabLayout indicotor ,tabmoviesactions ;
    private RecyclerView moviesRv ,moviesRvWeek ,tab ;
    ProgressDialog progressDialog ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        progressDialog = new ProgressDialog(this);
        inViews();
        addAllMovies();
        inPopularMovies();
        inWeekMovies();
        moviesViewTab();
    }
    private void addAllMovies(){
        uploads = new ArrayList<>();
        uploadslistLatests = new ArrayList<>();
        uploadslistPopular = new ArrayList<>();
        actionmovies = new ArrayList<>();
        advanturemovies = new ArrayList<>();
        comedymovies = new ArrayList<>();
        sportmovies = new ArrayList<>();
        romanticmovies = new ArrayList<>();
        uploadsSlider = new ArrayList<>();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("videos");
        progressDialog.setMessage("loding...");
        progressDialog.show();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    GetVideoDetails upload = postSnapshot.getValue(GetVideoDetails.class);
                    SliderSide slide = postSnapshot.getValue(SliderSide.class);
                    if (upload.getVideo_type().equals("latest movies")){
                        uploadslistLatests.add(upload);
                    }
                    if (upload.getVideo_type().equals("best popular movies")){
                        uploadslistPopular.add(upload);
                    }
                    if (upload.getVideo_category().equals("Action ")){
                        actionmovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Adventure")){
                        advanturemovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Comedy")){
                        comedymovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Romantic")){
                        romanticmovies.add(upload);
                    }
                    if (upload.getVideo_category().equals("Sports")){
                       sportmovies.add(upload);
                    }
                    if (upload.getVideo_slide().equals("Slide movies")){
                        uploadsSlider.add(slide);
                    }
                    uploads.add(upload);
                }
                iniSlider();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void iniSlider() {
        SliderPagerAdapterNew adapterNew = new SliderPagerAdapterNew(this,uploadsSlider);
        sliderPager.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
        indicotor.setupWithViewPager(sliderPager,true);
    }
    private void inWeekMovies(){
        movieShowAdapter = new MovieShowAdapter(this,uploadslistLatests,this);
        moviesRvWeek.setAdapter(movieShowAdapter);
        moviesRvWeek.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }
    private void inPopularMovies(){
        movieShowAdapter = new MovieShowAdapter(this,uploadslistPopular,this );
        moviesRv.setAdapter(movieShowAdapter);
        moviesRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();

    }


    private void moviesViewTab(){
        getActionMovies();
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Action"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Advanture"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Comedy"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Romantic"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Sports"));
        tabmoviesactions.setTabGravity(TabLayout.GRAVITY_FILL);
        tabmoviesactions.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabmoviesactions.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 : getActionMovies();
                        break;
                    case 1 : getAdvantureMovies();
                        break;
                    case 2 : getComedyMovies();
                        break;
                    case 3 : getRomanticMovies();
                        break;
                    case 4 :getSportMovies();
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void inViews(){
        tabmoviesactions = findViewById(R.id.tabActionMovies);
        sliderPager = findViewById(R.id.slider_pager);
        indicotor= findViewById(R.id.indictor);
        moviesRvWeek = findViewById(R.id.rv_movies_week);
        moviesRv = findViewById(R.id.Rv_movies);
        tab = findViewById(R.id.tabrecycler);



    }

    @Override
    public void onMovieClick(GetVideoDetails movie, ImageView imageView) {
        Intent in = new Intent(this , MovieDetailsActivity.class);
        in.putExtra("title", movie.getVideo_name());
        in.putExtra("imgURL", movie.getVideo_thumb());
        in.putExtra("imgCover", movie.getVideo_thumb());
        in.putExtra("movieDetails",movie.video_description);
        in.putExtra("movieUrl", movie.getVideo_url());
        in.putExtra("movieCategory", movie.getVideo_category());
        ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,imageView,"sharedName");
        startActivity(in,options.toBundle());


    }

    public class SliderTimer extends TimerTask {
        public void run(){
            if (sliderPager.getCurrentItem()<uploadsSlider.size()-1){
                sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);
            }
            else {
                sliderPager.setCurrentItem(0);
            }

        }
    }
    private void getActionMovies(){
        movieShowAdapter = new MovieShowAdapter(this,actionmovies,this);
        tab.setAdapter(movieShowAdapter);
       tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }
    private void getSportMovies(){
        movieShowAdapter = new MovieShowAdapter(this,sportmovies,this);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }
    private void getRomanticMovies(){
        movieShowAdapter = new MovieShowAdapter(this,romanticmovies,this);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }
    private void getComedyMovies(){
        movieShowAdapter = new MovieShowAdapter(this,comedymovies,this);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }
    private void getAdvantureMovies(){
        movieShowAdapter = new MovieShowAdapter(this,advanturemovies,this);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        movieShowAdapter.notifyDataSetChanged();
    }

}