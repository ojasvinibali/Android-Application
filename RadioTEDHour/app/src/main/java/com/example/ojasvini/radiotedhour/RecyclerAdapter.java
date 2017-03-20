package com.example.ojasvini.radiotedhour;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Ojasvini on 3/18/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RadioViewHolder> {
    private Context context;
    private List<Podcast> podcastList;


    public RecyclerAdapter(Context context, List<Podcast> podcastList) {
        this.context = context;
        this.podcastList = podcastList;
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, null);
        RadioViewHolder appViewHolder = new RadioViewHolder(inflatedView);
        return  appViewHolder;
    }

    @Override
    public void onBindViewHolder(RadioViewHolder holder, final int position) {

        final Podcast podcast = podcastList.get(position);
        Picasso.with(context)
                .load(podcast.getImgURL().toString())
                .into(holder.imageView);
        holder.textView.setText(podcast.getTitle());
        holder.textView1.setText("Posted: "+podcast.getPubdate().substring(0,16));
        holder.textView2.setText("Play Now");
        holder.imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.setMedia(podcast);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != podcastList ? podcastList.size() : 0);
    }

    class RadioViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView1;
        protected TextView textView2;
        protected  ImageView imageView2;

        public RadioViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageViewAppImage);
            this.textView = (TextView) view.findViewById(R.id.textViewTitle);
            this.textView1 = (TextView) view.findViewById(R.id.textViewDate);
            this.textView2 = (TextView) view.findViewById(R.id.textViewPlayNow);
            this.imageView2 =(ImageView) view.findViewById(R.id.imageViewPlay);

        }
    }
}
