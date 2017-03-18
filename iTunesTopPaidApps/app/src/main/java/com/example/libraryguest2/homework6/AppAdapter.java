package com.example.libraryguest2.homework6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ojasv on 2/25/2017.
 */

public class AppAdapter extends ArrayAdapter<Apps> {
    List<Apps> mData;
    Context mContext;
    int mResource;

    public AppAdapter(Context context, int resource, List<Apps> objects) {
        super(context, resource, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        Apps apps = mData.get(position);
        ImageView img = (ImageView)convertView.findViewById(R.id.appImage);
        TextView text = (TextView)convertView.findViewById(R.id.appDetails);
        RadioButton radio = (RadioButton)convertView.findViewById(R.id.radioButton);
        if(apps.getIsFavorite()){
            radio.setBackgroundResource(R.drawable.black_star);
        }
        else {
            radio.setBackgroundResource(R.drawable.white_star);
        }
        Picasso.with(mContext)
                .load(apps.getImgUrl().toString())
                .into(img);
        text.setText(apps.getName()+ "\n Price: "+apps.getPrice());
        return convertView;
    }
}
