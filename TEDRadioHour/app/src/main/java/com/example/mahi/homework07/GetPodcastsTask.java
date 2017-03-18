package com.example.mahi.homework07;

import android.os.AsyncTask;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class GetPodcastsTask extends AsyncTask<String, Void, ArrayList<Podcast>> {
    IGetPodCasts activity;

    public GetPodcastsTask(IGetPodCasts activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                return PodcastUtil.PodcastPullParser.parsePodcasts(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final ArrayList<Podcast> podcasts) {
        super.onPostExecute(podcasts);
        activity.getpodcasts(podcasts);
    }

    public static interface IGetPodCasts{
        public void getpodcasts(ArrayList<Podcast> podcastList);
    }
}