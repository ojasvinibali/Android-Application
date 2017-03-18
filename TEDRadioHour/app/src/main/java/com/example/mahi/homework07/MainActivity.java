package com.example.mahi.homework07;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GetPodcastsTask.IGetPodCasts{

    public static final String APP_KEY="app";
    public static final String TITLE_KEY="title";
    public static final String MEDIA_KEY="media";
    public static final String DURATION_KEY="duration";
    public static final String DESCRIPTION_KEY="desc";
    public static final String PUBLICATIONDATE_KEY="pub";
    public static boolean change=false;
    public static String image=null;
    ProgressDialog progressDialog;

    public static ProgressBar audioProgress;
    public static ImageView imageView;

    static RecyclerView recyclerView;
    static RecyclerAdapter mAdapter;
    ArrayList<Podcast> podcasts =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ted_logo);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Episodes");
        progressDialog.setMax(100);
        progressDialog.show();


        imageView= (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.pause);
        imageView.setVisibility(ImageView.GONE);

        audioProgress = new ProgressBar(MainActivity.this,null,android.R.attr.progressBarStyleHorizontal);
        audioProgress.setMax(100);
        audioProgress = (ProgressBar) findViewById(R.id.progressBar2);
        audioProgress.setVisibility(ProgressBar.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new RecyclerAdapter(this, podcasts,change);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            new GetPodcastsTask(MainActivity.this).execute("https://www.npr.org/rss/podcast.php?id=510298");
        }
        else
        {
            Toast.makeText(this, "Not connected to internet!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.action_refresh)
        {
            imageView.setVisibility(ImageView.GONE);
            audioProgress.setVisibility(ProgressBar.GONE);
            RecyclerAdapter.mediaPlayer.stop();
            audioProgress.setProgress(0);
            change = !change;
            if(!change){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

            }else{
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }
        }
        return true;
    }


    @Override
    public void getpodcasts(ArrayList<Podcast> podcastList) {

        if (podcastList != null){
            podcasts=podcastList;

            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

            if(change){
                mAdapter = new RecyclerAdapter(this,R.layout.grid_item_layout, podcasts,change);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            else {
                mAdapter = new RecyclerAdapter(this, R.layout.row_item_layout, podcasts,change);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

        }
        progressDialog.dismiss();
    }
}
