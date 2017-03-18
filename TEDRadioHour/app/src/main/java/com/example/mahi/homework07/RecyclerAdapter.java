package com.example.mahi.homework07;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Podcast> podcasts;
    Context context;
    static boolean flag;
    boolean change;
    static MediaPlayer mediaPlayer = new MediaPlayer();

    public RecyclerAdapter(Activity activity, ArrayList<Podcast> podcasts, boolean change) {

        this.context = activity;
        this.podcasts = podcasts;
        this.change=change;
    }

    public RecyclerAdapter(Activity activity, int resource, ArrayList<Podcast> podcasts, boolean change) {
        this.podcasts = podcasts;
        this.context=activity;
        this.change=change;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView row_title, pubDate,grid_title;
        public ImageView imageurl;
        public ImageButton row_play;
        public ImageView grid_play;

        public MyViewHolder(View view) {
            super(view);
            if (!MainActivity.change)
            {
                row_title = (TextView) view.findViewById(R.id.podcast_name);
                pubDate = (TextView) view.findViewById(R.id.publish_date);
                imageurl = (ImageView) view.findViewById(R.id.imageView);
                row_play = (ImageButton) view.findViewById(R.id.play_button);
            }
            else
            {

                grid_title=(TextView) view.findViewById(R.id.grid_title);
                imageurl = (ImageView) view.findViewById(R.id.image_grid);
                grid_play = (ImageView) view.findViewById(R.id.grid_play);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!MainActivity.change)
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_layout, parent, false);
            return new MyViewHolder(itemView);
        }
        else
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_layout, parent, false);
            return new MyViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            if(!MainActivity.change)
            {
                holder.row_title.setText(podcasts.get(position).getTitle());
                holder.pubDate.setText(podcasts.get(position).getPubDate());
                Picasso.with(context).load(podcasts.get(position).getImgURL()).into(holder.imageurl);


                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        sendToIntent(position);
                        return true;
                    }
                });

               holder.row_play.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        setMedia(position);
                        return true;
                    }
                });

            }
        else
            {
                holder.grid_title.setText(podcasts.get(position).getTitle());
                Picasso.with(context).load(podcasts.get(position).getImgURL()).into(holder.imageurl);

                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        sendToIntent(position);
                        return true;
                    }
                });

                holder.grid_play.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        setMedia(position);
                        return true;
                    }
                });
            }

        MainActivity.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=!flag;
                if (flag) {
                    MainActivity.imageView.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else{
                    MainActivity.imageView.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });
    }

    private void sendToIntent(int position)
    {
        mediaPlayer.stop();
        MainActivity.imageView.setVisibility(ImageView.GONE);
        MainActivity.audioProgress.setVisibility(ProgressBar.GONE);

        Intent intent = new Intent(context,PlayActivity.class);
        intent.putExtra(MainActivity.APP_KEY,podcasts.get(position).getImgURL());
        intent.putExtra(MainActivity.TITLE_KEY,podcasts.get(position).getTitle());
        intent.putExtra(MainActivity.MEDIA_KEY,podcasts.get(position).getMp3url());
        intent.putExtra(MainActivity.DURATION_KEY,podcasts.get(position).getDuration());
        intent.putExtra(MainActivity.DESCRIPTION_KEY,podcasts.get(position).getDescription());
        intent.putExtra(MainActivity.PUBLICATIONDATE_KEY,podcasts.get(position).getPubDate());

        context.startActivity(intent);
    }

    private void setMedia(int position)
    {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(podcasts.get(position).getMp3url());
            mediaPlayer.prepare();
            mediaPlayer.start();

            MainActivity.audioProgress.setVisibility(ProgressBar.VISIBLE);
            MainActivity.imageView.setVisibility(ImageView.VISIBLE);
            MainActivity.imageView.setImageResource(R.drawable.pause);
            MainActivity.audioProgress.setProgress(0);

            final int duration = Integer.parseInt(podcasts.get(position).getDuration());
            MainActivity.audioProgress.setMax(duration*100);

            new CountDownTimer(duration*100, duration){
                @Override
                public void onTick(long millisUntilFinished){
                    if (mediaPlayer.isPlaying()){
                        MainActivity.audioProgress.setProgress(MainActivity.audioProgress.getProgress() + duration);
                    }
                }

                @Override
                public void onFinish(){
                    mediaPlayer.stop();
                    MainActivity.imageView.setVisibility(ImageView.GONE);
                    MainActivity.audioProgress.setVisibility(ProgressBar.GONE);
                    return;
                }

            }.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return podcasts.size();

    }
}