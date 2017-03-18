package com.example.ojasv.homework4;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ojasv on 2/10/2017.
 */
public class GetQuizAsyncTask extends AsyncTask<String,Object,ArrayList<Quiz>>{
    Idata activity;
    public GetQuizAsyncTask(Idata activity){
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Quiz> quiz ) {
        super.onPostExecute(quiz);
        Log.i("Async task: quiz", String.valueOf(quiz));
        activity.setUpData(quiz);
        activity.stopProgress();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showProgress();
    }

    @Override
    protected ArrayList<Quiz> doInBackground(String... params) {
        ArrayList <Quiz> quiz ;

        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //reader.close();
                quiz= JSONParser.QuizJSONParser.parseQuestions(sb.toString());
                return quiz;

           /* JSONParser j = new JSONParser();
            JSONParser.NewsJSONParser sghj = new JSONParser.NewsJSONParser();
            sghj.parseNews(sb.toString());*/

            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }




    static public interface Idata {
        public void setUpData(ArrayList<Quiz> quiz);
        public void showProgress();
        public void stopProgress();
    }
}
