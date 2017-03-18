package com.example.libraryguest2.homework6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ojasv on 2/25/2017.
 */
public class JSONParser {
    static public class AppJSONParser {
        static ArrayList<Apps> parseApps(String in) throws JSONException {
            String name,price,imgUrl;
            ArrayList<Apps> result = new ArrayList<>();
            Apps app = null;
            JSONObject root = new JSONObject(in);
            JSONObject rootJSONObject = root.getJSONObject("feed");
            JSONArray jsonArray = rootJSONObject.getJSONArray("entry");
            for(int j = 0; j<jsonArray.length();j++) {
                JSONObject appObject = jsonArray.getJSONObject(j);
                JSONObject obj1 = appObject.getJSONObject("im:name");
                String titleLabel = obj1.getString("label");
                JSONObject obj2 = appObject.getJSONObject("im:price");
                String priceLabel = obj2.getString("label");
                JSONArray imgArray = appObject.getJSONArray("im:image");
                JSONObject img = imgArray.getJSONObject(0);
                imgUrl = img.getString("label");
                app = new Apps();
                app.setName(titleLabel);
                app.setPrice(priceLabel);
                app.setImgUrl(imgUrl);
                app.setIsFavorite(false);
                result.add(app);
            }
            Log.i("ArrayList",result.toString());
            return result;


        }
    }
}
