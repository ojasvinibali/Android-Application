package com.example.mahi.homework2;


/**
 * Created by ojasvini on 1/27/2017.
 */ 
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowbyyearActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList;
    Movie movie;
    Integer element = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbyyear);

        if (getIntent().getExtras() != null) {
            movieList = (ArrayList<Movie>) getIntent().getExtras().get(MainActivity.MOVIELIST_KEY);
        }

        ImageView first = (ImageView) findViewById(R.id.showbyyear_first);
        ImageView previous = (ImageView) findViewById(R.id.showbyyear_previous);
        ImageView next = (ImageView) findViewById(R.id.showbyyear_next);
        ImageView last = (ImageView) findViewById(R.id.showbyyear_last);


        Button finish = (Button) findViewById(R.id.showbyyear_finish);

        display(element);
        first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                display(0);
            }
        });

        previous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                element--;
                if(element<0) {
                    element++;
                    display(element);
                }else{
                    display(element);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                element++;

                if(element>=movieList.size()) {
                    element--;
                    display(element);
                }else{
                    display(element);
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                display(movieList.size()-1);
            }
        });


        finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void display(int postion){

        movie = movieList.get(postion);
        TextView name = (TextView) findViewById(R.id.showbyyear_name);
        name.setText(movie.getName());
        TextView desc = (TextView) findViewById(R.id.showbyyear_desc);
        desc.setText(movie.getDescription());
        TextView rating = (TextView) findViewById(R.id.showbyyear_rating);
        rating.setText(movie.getRating().toString());
        TextView genre = (TextView) findViewById(R.id.showbyyear_genre);
        genre.setText(movie.getGenre());
        TextView year = (TextView) findViewById(R.id.showbyyear_year);
        year.setText(movie.getYear().toString());
        TextView imdb = (TextView) findViewById(R.id.showbyyear_imdb);
        imdb.setText(movie.getImdb());
    }
}
