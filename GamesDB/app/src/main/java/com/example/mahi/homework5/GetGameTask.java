package com.example.mahi.homework5;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;


public class GetGameTask extends AsyncTask<String, Object, Game> {
    IGamedata activity;

    public GetGameTask(IGamedata activity)
    {
        this.activity = activity;
    }


    @Override
    protected Game doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();
                return GameUtil.GamePullParser.parseGame(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Game game) {
        super.onPostExecute(game);
        Log.d("demo",game.toString());
        try {
            activity.setupdata(game);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    static public interface IGamedata{
        public void setupdata(Game game) throws ParseException;
    }
}
