package com.example.ojasvini.radiotedhour;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ojasvini on 3/19/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.RadioViewHolder>{

        private Context context;
        private List<Podcast> podcastList;


        public GridAdapter(Context context, List<Podcast> podcastList) {
            this.context = context;
            this.podcastList = podcastList;
        }

        @Override
        public GridAdapter.RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_grid, null);
            GridAdapter.RadioViewHolder appViewHolder = new GridAdapter.RadioViewHolder(inflatedView);
            return  appViewHolder;
        }

        @Override
        public void onBindViewHolder(GridAdapter.RadioViewHolder holder, int position) {

            Podcast podcast = podcastList.get(position);
            Picasso.with(context)
                    .load(podcast.getImgURL().toString())
                    .into(holder.imageView2);
            holder.textView.setText(podcast.getTitle());
        }

        @Override
        public int getItemCount() {
            return (null != podcastList ? podcastList.size() : 0);
        }

        class RadioViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView2;
            protected TextView textView;
            protected ImageButton imageButton;


            public RadioViewHolder(View view) {
                super(view);
                this.imageView2 = (ImageView) view.findViewById(R.id.imageView2);
                this.textView = (TextView) view.findViewById(R.id.textView);
                this.imageButton =(ImageButton) view.findViewById(R.id.imageButton);

            }
        }
    }

