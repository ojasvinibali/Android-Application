package com.example.mahi.homework2;


/**
 * Created by ojasvini on 1/27/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static Integer ADD_CODE = 100;
    final static Integer EDIT_CODE = 101;
    final static String MOVIELIST_KEY = "movielist";
    final static String NEWMOVIE_KEY = "newmovie";
    final static String ACTION = "action";
    final static String ADDACTION_KEY = "add";
    final static String DELACTION_KEY = "delete";
    final static String EDITACTION_KEY = "edit";
    final static String EDITSELECT_KEY = "editselect";
    final static String BYYEARACTION_KEY = "byyear";
    final static String BYRATINGACTION_KEY = "byrating";
    private ArrayList<Movie> movieList = new ArrayList();
    Movie new_movie,edit_movie;
    String action;
    Integer selection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_button = (Button) findViewById(R.id.add_button);
        Button edit_button = (Button) findViewById(R.id.edit_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button listyear_button = (Button) findViewById(R.id.listyear_button);
        Button listrating_button = (Button) findViewById(R.id.listrating_button);

        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,ADD_CODE);
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener(){
            Movie movie;
            @Override
            public void onClick(View v) {

                if(!movieList.isEmpty())
                {
                    final String[] names = new String[movieList.size()];
                    for(int i=0;i<movieList.size();i++)
                    {
                        movie = movieList.get(i);
                        names[i]=movie.getName();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Toast.makeText(MainActivity.this, names[item], Toast.LENGTH_SHORT).show();
                            selection=item;

                            Intent intent = new Intent(MainActivity.this,EditActivity.class);
                            intent.putExtra(MOVIELIST_KEY,movieList);
                            intent.putExtra(EDITSELECT_KEY,selection);
                            startActivityForResult(intent,EDIT_CODE);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No movie in the list", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener(){
            Movie movie;
            @Override
            public void onClick(View v) {
                if(!movieList.isEmpty())
                {
                    final String[] names = new String[movieList.size()];
                    for(int i=0;i<movieList.size();i++)
                    {
                        movie = movieList.get(i);
                        names[i]=movie.getName();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Toast.makeText(MainActivity.this, "Deleted the movie "+names[item], Toast.LENGTH_SHORT).show();
                            movieList.remove(item);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No movie in the list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listyear_button.setOnClickListener(new View.OnClickListener(){
            ArrayList<Movie> yearsortmovielist;
            @Override
            public void onClick(View v) {
                if(movieList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No movie in the list", Toast.LENGTH_SHORT).show();
                }
                else{
                    yearsortmovielist = movieList;
                            Collections.sort(yearsortmovielist, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie1, Movie movie2)
                        {

                            return  movie1.year.compareTo(movie2.year);
                        }
                    });
                    Intent intent = new Intent(MainActivity.this,ShowbyyearActivity.class);
                    intent.putExtra(MOVIELIST_KEY,yearsortmovielist);
                    startActivity(intent);
                }
            }
        });

        listrating_button.setOnClickListener(new View.OnClickListener(){
            ArrayList<Movie> ratingsortmovielist;
            @Override
            public void onClick(View v) {
                if(movieList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No movie in the list", Toast.LENGTH_SHORT).show();
                }
                else{
                    ratingsortmovielist = movieList;
                    Collections.sort(ratingsortmovielist, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie1, Movie movie2)
                        {

                            return  movie1.rating.compareTo(movie2.year);
                        }
                    });
                    Intent intent = new Intent(MainActivity.this,ShowbyratingActivity.class);
                    intent.putExtra(MOVIELIST_KEY,ratingsortmovielist);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                new_movie = (Movie) data.getExtras().get(NEWMOVIE_KEY);
                movieList.add(new_movie);
            }
        }
        else if(requestCode==EDIT_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                edit_movie = (Movie) data.getExtras().get(EDITACTION_KEY);
                movieList.set(selection,edit_movie);
            }
        }
    }
}
