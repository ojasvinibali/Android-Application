package com.example.mahi.homework5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.sephiroth.android.library.picasso.Picasso;

import static java.lang.Math.floor;

public class GameDetails extends AppCompatActivity implements GetGameTask.IGamedata {
    Game fetchGame;
    final static String IDLIST_KEY = "idlist";
    final static String VIDEO_URL_KEY = "videourl";
    final static String GAME_NAME_KEY = "gamename";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        int id = getIntent().getIntExtra(MainActivity.GAME_ID,0);

        String url = "http://thegamesdb.net/api/GetGame.php?id="+id;

        new GetGameTask(GameDetails.this).execute(url);
        findViewById(R.id.game_load).setVisibility(View.VISIBLE);
        findViewById(R.id.similar_button).setEnabled(false);
        findViewById(R.id.trailer_button).setEnabled(false);

        findViewById(R.id.trailer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoLink = fetchGame.getYoutubeLink();
                String embedLink = null;

                String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(videoLink);

                if(matcher.find()){
                    embedLink = "http://www.youtube.com/embed/"+matcher.group();
                }

                Intent intent = new Intent(GameDetails.this,TrailerView.class);
                intent.putExtra(VIDEO_URL_KEY,embedLink);
                intent.putExtra(GAME_NAME_KEY,fetchGame.getTitle());
                startActivity(intent);


            }
        });

        findViewById(R.id.similar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetails.this,SimilarGames.class);
                intent.putExtra(IDLIST_KEY,fetchGame.similar);
                startActivity(intent);
            }
        });

        findViewById(R.id.finish_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void setupdata(Game game){
        fetchGame = game;

        TextView title = (TextView) findViewById(R.id.game_title);
        title.setText(game.getTitle());

        TextView overView = (TextView) findViewById(R.id.overview_text);
        overView.setText(game.getOverview());

        ImageView imageView = (ImageView) findViewById(R.id.game_image);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Picasso.with(this)
                .load(game.getUrlToImage())
                .placeholder(R.drawable.progress_animation)
                .resize(((int)(height*0.2)),((int) (width*0.2)))
                .into(imageView);

        TextView genre = (TextView) findViewById(R.id.genre_text);
        genre.setText(game.getGenre());

        TextView publisher = (TextView) findViewById(R.id.publisher_text);
        publisher.setText(game.getPublisher());


        if(game.similar!=null)
        {
            findViewById(R.id.similar_button).setEnabled(true);
        }

        String youtubeLink = game.getYoutubeLink();

        if(youtubeLink != null)
        {
            findViewById(R.id.trailer_button).setEnabled(true);
        }

        findViewById(R.id.game_load).setVisibility(View.GONE);
    }
}
