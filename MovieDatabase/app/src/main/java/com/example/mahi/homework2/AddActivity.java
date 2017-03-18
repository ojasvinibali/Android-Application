package com.example.mahi.homework2;

/**
 * Created by ojasvini on 1/27/2017.
 */ 
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Spinner genre_spinner;
    SeekBar rating_seekbar;
    TextView movie_rating_seek;
    Movie new_movie = new Movie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        genre_spinner = (Spinner) findViewById(R.id.genre_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre_spinner.setAdapter(adapter);

        movie_rating_seek = (TextView) findViewById(R.id.add_movie_rating_number);



        rating_seekbar = (SeekBar) findViewById(R.id.add_movie_rating_seekbar);

        rating_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                movie_rating_seek.setText(""+ progressChanged + "");
            }
        });

        Button add_movie_button = (Button) findViewById(R.id.add_movie_button);
        add_movie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie_name = ((EditText)findViewById(R.id.add_movie_name_input)).getText().toString();
                String movie_desc = ((EditText) findViewById(R.id.add_movie_description_input)).getText().toString();
                String movie_genre = genre_spinner.getSelectedItem().toString();
                String movie_rating = String.valueOf(rating_seekbar.getProgress());
                String movie_year = ((EditText) findViewById(R.id.add_movie_year_input)).getText().toString();
                String movie_imdb = ((EditText) findViewById(R.id.add_movie_imdb_input)).getText().toString();
                String error_message = "";
                Boolean flag=false;

                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);

                if(movie_name.length()>0&&movie_name.length()<50)
                {
                    new_movie.setName(movie_name);
                }
                else if(movie_name.length()>50)
                {
                    flag = true;
                    error_message += "\nName : More than 50 Characters";
                }
                else
                {
                    flag =true;
                    error_message += "\nName: Name Required";
                }

                if(movie_desc.length()>0&&movie_desc.length()<1000)
                {
                    new_movie.setDescription(movie_desc);
                }
                else if(movie_desc.length()>1000)
                {
                    flag =true;
                    error_message+="\nDescription: More than 1000 characters.";
                }
                else
                {
                    flag =true;
                    error_message+="\nDescription: Description required.";
                }

                if(movie_genre.length()==0)
                {
                    flag=true;
                    error_message+="\nGenre: Select one.";
                }
                else if (movie_genre.equals("Select"))
                {
                    flag=true;
                    error_message+="\nGenre: Select one.";
                }
                else
                {
                    new_movie.setGenre(movie_genre);
                }

                if(movie_imdb.equals(""))
                {
                    flag =true;
                    error_message+="\nIMDB: Link must be provided.";
                }
                else
                {
                    new_movie.setImdb(movie_imdb);
                }

                if(movie_year.length()==4)
                {
                    new_movie.setYear(Integer.parseInt(movie_year));
                }
                else
                {
                    flag =true;
                    error_message += "\nYear: Invalid year.";
                }

                if(movie_rating.equals("1")||movie_rating.equals("2")||movie_rating.equals("3")||movie_rating.equals("4")||movie_rating.equals("5"))
                {
                    new_movie.setRating(Integer.parseInt(movie_rating));
                }
                else
                {
                    flag =true;
                    error_message += "\nRating: Improper rating.";
                }

                if(flag)
                {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, error_message, Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.NEWMOVIE_KEY,new_movie);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
