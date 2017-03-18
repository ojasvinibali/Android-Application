package com.example.mahi.homework07;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();
    ProgressBar audioseek;
    static boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ted_logo);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("Play!");

        String imgurl = getIntent().getExtras().getString(MainActivity.APP_KEY);
        String podcastTitle = getIntent().getExtras().getString(MainActivity.TITLE_KEY);
        final String mp3url = getIntent().getExtras().getString(MainActivity.MEDIA_KEY);
        final String desc = getIntent().getExtras().getString(MainActivity.DESCRIPTION_KEY);
        String pubdate = getIntent().getExtras().getString(MainActivity.PUBLICATIONDATE_KEY);
        final int duration = Integer.parseInt(getIntent().getExtras().getString(MainActivity.DURATION_KEY));

        pubdate= pubdate.substring(4,pubdate.length());

        Date varDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            varDate=dateFormat.parse(pubdate);
            dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            TextView date = (TextView)findViewById(R.id.publication_text);
            date.setText(dateFormat.format(varDate));
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



        ImageView imageView = (ImageView) findViewById(R.id.podcast_image);
        Picasso.with(PlayActivity.this).load(imgurl).into(imageView);

        final ImageButton playpause = (ImageButton) findViewById(R.id.play_pause);
        playpause.setImageResource(R.drawable.play);

        TextView description = (TextView)findViewById(R.id.description_text);
        description.setText(desc);

        TextView title = (TextView)findViewById(R.id.podcast_title);
        title.setText(podcastTitle);

        TextView duration_text = (TextView) findViewById(R.id.duration_text);
        int minutes = duration/60;
        int seconds = duration%60;
        duration_text.setText(minutes+" Minutes and "+seconds+" Seconds");


        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mp3url);
            mediaPlayer.prepare();

            audioseek = (ProgressBar) findViewById(R.id.progressBar);
            audioseek.setVisibility(ProgressBar.VISIBLE);
            audioseek.setProgress(0);

        }catch (IOException e) {
            e.printStackTrace();
        }

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=!flag;
                if(flag){
                    playpause.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                    audioseek.setMax(duration * 1000);
                    final long dur = duration;

                    new CountDownTimer(duration * 1000, duration) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (mediaPlayer.isPlaying()) {
                                audioseek.setProgress(audioseek.getProgress() + duration);
                            }
                        }

                        @Override
                        public void onFinish() {
                            mediaPlayer.stop();
                            return;

                        }

                    }.start();


                }else{
                    playpause.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
        mediaPlayer.stop();
    }
}
