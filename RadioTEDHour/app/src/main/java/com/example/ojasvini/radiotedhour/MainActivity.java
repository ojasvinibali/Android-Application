package com.example.ojasvini.radiotedhour;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioAsyncTask.Idata{
    String url ="https://www.npr.org/rss/podcast.php?id=510298";
    static ProgressDialog dialog;
    RecyclerAdapter recadapter;
    GridAdapter gridAdapter;
    private RecyclerView mRecyclerView;
    ArrayList<Podcast> mainList = new ArrayList<>();
    public static String image = null;
    public static boolean grid=  false;
    ImageView imageView;
    static MediaPlayer mediaPlayer = new MediaPlayer();
   static ImageButton imagebutton2;
    public static TextView textView4,textView5;
    static SeekBar seekBar;
    static boolean play = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RadioAsyncTask(MainActivity.this).execute(url);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imagebutton2 =(ImageButton)findViewById(R.id.imageButton2);
        textView4 =(TextView) findViewById(R.id.textView4);
        textView5 =(TextView) findViewById(R.id.textView5);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!grid){
                    grid = true;
                    displayGrid(mainList);
                }
                else {
                    grid = false;
                    displayList(mainList);
                }
            }
        });
    }
    @Override
    public void showProgress() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    public void setupdata(ArrayList<Podcast> podcast)  {
      mainList =podcast;
        Log.d("in main",mainList.toString());
        if(grid)
            displayGrid(mainList);
        else
            displayList(mainList);
    }

    private void displayList(ArrayList<Podcast> mainList) {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recadapter = new RecyclerAdapter(MainActivity.this,mainList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(recadapter);
    }

    private void displayGrid(ArrayList<Podcast> mainList) {
        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        gridAdapter = new GridAdapter(MainActivity.this,mainList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gridAdapter);
    }


    public static void setMedia(Podcast podcast){
        try {
            final int duration = podcast.getDuration();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(podcast.getMediaURL());
            mediaPlayer.prepare();
            mediaPlayer.start();
            textView4.setText(podcast.getTitle());
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setProgress(0);
            imagebutton2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                 if(play){
                     play = true;
                     mediaPlayer.start();
                     imagebutton2.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                 }
                 else{
                     play = false;
                     mediaPlayer.pause();
                     imagebutton2.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                 }
                     return play;
                }
            });
            new CountDownTimer(duration * 100, duration) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(mediaPlayer.isPlaying()){
                        imagebutton2.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                        seekBar.setProgress(seekBar.getProgress() + duration);
                    }
                }

                @Override
                public void onFinish() {
                mediaPlayer.stop();
                    textView5.setText("0:00");
                    seekBar.setProgress(0);
                    imagebutton2.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                    return ;
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopProgress() {
        dialog.dismiss();
    }
}
