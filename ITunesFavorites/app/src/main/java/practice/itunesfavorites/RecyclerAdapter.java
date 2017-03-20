package practice.itunesfavorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ninaadpai on 3/17/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    private List<Filter> feedItemList;
    private Context mContext;

    public RecyclerAdapter(Context context, List<Filter> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Filter feedItem = feedItemList.get(i);

        Picasso.with(mContext)
                .load(feedItem.getUrl().toString())
                .into(customViewHolder.imageView);
        customViewHolder.textView.setText(feedItem.getName());
        customViewHolder.textView1.setText("$"+Double.parseDouble(String.valueOf(feedItem.getPrice())));
        double dollarprice = feedItem.getPrice();
        if (dollarprice <= 1.99){
            customViewHolder.dollarimage.setImageResource(R.drawable.price_low);
        }
        else if(dollarprice >=2.00 && dollarprice <=5.99){
            customViewHolder.dollarimage.setImageResource(R.drawable.price_medium);
        }
        else if (dollarprice >=6.00){
            customViewHolder.dollarimage.setImageResource(R.drawable.price_high);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView1;
        protected ImageView dollarimage;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView3);
            this.dollarimage = (ImageView)view.findViewById(R.id.imageView4);
            this.textView = (TextView) view.findViewById(R.id.textView3);
            this.textView1 = (TextView) view.findViewById(R.id.textView4);

        }
    }
}