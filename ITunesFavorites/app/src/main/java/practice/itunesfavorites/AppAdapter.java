package practice.itunesfavorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ninaadpai on 3/16/2017.
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
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);
        TextView text = (TextView)convertView.findViewById(R.id.textView);
        TextView price = (TextView) convertView.findViewById(R.id.textView2);
        ImageView dollars = (ImageView)convertView.findViewById(R.id.imageView2);
        double dollarprice = Double.parseDouble(apps.getPrice().substring(1, apps.getPrice().toString().length()));
        if (dollarprice <= 1.99){
            dollars.setImageResource(R.drawable.price_low);
        }
        else if(dollarprice >=2.00 && dollarprice <=5.99){
            dollars.setImageResource(R.drawable.price_medium);
        }
        else if (dollarprice >=6.00){
            dollars.setImageResource(R.drawable.price_high);
        }
        Picasso.with(mContext)
                .load(apps.getImgUrl().toString())
                .into(img);
        text.setText(apps.getName());
        price.setText(apps.getPrice());
        return convertView;
    }
}