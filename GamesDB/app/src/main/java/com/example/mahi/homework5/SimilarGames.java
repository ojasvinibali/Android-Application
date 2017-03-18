package com.example.mahi.homework5;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SimilarGames extends AppCompatActivity implements GetSimilarTask.ISimilardata {
    ArrayList<Integer> idList;
    ArrayList<Game> gameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_games);

        idList = new ArrayList<>();
        idList = getIntent().getIntegerArrayListExtra(GameDetails.IDLIST_KEY);

        String baseUrl = "http://thegamesdb.net/api/GetGame.php?id=";


        new GetSimilarTask(this).execute(idList);

        findViewById(R.id.similar_load).setVisibility(View.VISIBLE);

        findViewById(R.id.finish_similar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setupdata(ArrayList<Game> games){
        gameList = games;
        findViewById(R.id.similar_load).setVisibility(View.GONE);

        LinearLayout similar_list = (LinearLayout) findViewById(R.id.similar_list);

        SimpleDateFormat informat = new SimpleDateFormat("mm/dd/yyyy");
        SimpleDateFormat outformat = new SimpleDateFormat("yyyy");
        String date = null;

        for(int i=0;i<gameList.size();i++)
        {
            TextView new_text = new TextView(this);
            try {
                date = outformat.format(informat.parse(gameList.get(i).getReleaseDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new_text.setText(gameList.get(i).getTitle()+". Published in: "+date+". Platform: "+gameList.get(i).getPlatform());

            similar_list.addView(new_text);
        }
    }
}
