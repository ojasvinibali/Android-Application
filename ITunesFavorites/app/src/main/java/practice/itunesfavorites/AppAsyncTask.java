package practice.itunesfavorites;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ojasv on 2/25/2017.
 */

public class AppAsyncTask extends AsyncTask<String,Object,ArrayList<Apps>> {
    IApp iapp;
    public AppAsyncTask(IApp iapp) {
        this.iapp = iapp;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        iapp.showProgress();
    }

    @Override
    protected void onPostExecute(ArrayList<Apps> appses) {
        super.onPostExecute(appses);
        iapp.setUpData(appses);
        iapp.stopProgress();
    }

    @Override
    protected ArrayList<Apps> doInBackground(String... params) {
        ArrayList<Apps> apps;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line= "";
                while((line = reader.readLine())!=null) {
                    sb.append(line + "\n");
                }
                apps = JSONParser.AppJSONParser.parseApps(sb.toString());
                return apps;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public interface IApp {

        void showProgress();
        void setUpData(ArrayList<Apps> apps);
        void stopProgress();
    }
}

