package com.example.ojasv.homework4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ojasv on 2/12/2017.
 */

public class GetImageQuizAsyncTask extends AsyncTask<String,Void,Bitmap> {
    IQuestion activity;
   // int questionIndex =0;
    public GetImageQuizAsyncTask(IQuestion activity){
        this.activity = activity;
    }

@Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.startProgress();
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setupData(bitmap);
        activity.stopProgress();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public interface IQuestion {
        public void setupData(Bitmap image);

        public void startProgress();

        public void stopProgress();
    }
}
