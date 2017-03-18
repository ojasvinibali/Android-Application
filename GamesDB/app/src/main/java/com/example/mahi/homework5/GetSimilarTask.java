package com.example.mahi.homework5;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ojasvini on 2/6/2017.
 */

public class GetSimilarTask extends AsyncTask<ArrayList<Integer>, Object, ArrayList<Game>> {
    ISimilardata activity;
    ArrayList<Game> gameList;

    public GetSimilarTask(ISimilardata activity)
    {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Game> doInBackground(ArrayList<Integer>... params) {

        String baseUrl = "http://thegamesdb.net/api/GetGame.php?id=";
        gameList = new ArrayList<>();
        for(int i=0; i<params[0].size();i++)
        {
            try {
                URL url = new URL(baseUrl+params[0].get(i));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                if(statusCode == HttpURLConnection.HTTP_OK)
                {
                    InputStream in = con.getInputStream();
                    gameList.add(SimilarListUtil.GamePullParser.parseGame(in));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gameList;
    }

    @Override
    protected void onPostExecute(ArrayList<Game> games) {
        super.onPostExecute(games);
        activity.setupdata(games);

    }

    static public interface ISimilardata{
        public void setupdata(ArrayList<Game> games);
    }
}
