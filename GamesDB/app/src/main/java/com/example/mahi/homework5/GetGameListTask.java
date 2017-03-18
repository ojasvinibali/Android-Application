package com.example.mahi.homework5;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ojasvini on 2/6/2017.
 */

public class GetGameListTask extends AsyncTask<String, Object, ArrayList<Game>> {
    Idata activity;

    public GetGameListTask(Idata activity)
    {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Game> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();
                return GameListUtil.GamePullParser.parseGame(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Game> games) {
        super.onPostExecute(games);
        Log.d("demo",games.toString());
        try {
            activity.setupdata(games);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static public interface Idata{
        public void setupdata(ArrayList<Game> games) throws ParseException;
    }
}
