package com.example.ojasvini.radiotedhour;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ojasvini on 3/18/2017.
 */

public class RadioAsyncTask extends AsyncTask<String,Object,ArrayList<Podcast>> {

    Idata activity;

    public RadioAsyncTask(Idata activity)
    {
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showProgress();
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        ArrayList<Podcast> podl ;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                podl = PodcastXMLPullParser.ListPullParser.parseList(in);
                return podl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> podcastArrayList) {
        super.onPostExecute(podcastArrayList);
        activity.stopProgress();
        activity.setupdata(podcastArrayList);
    }

    static public interface Idata{
        public void setupdata(ArrayList<Podcast> podcastArrayList) ;
        public void stopProgress();
        public void showProgress();
    }
}

