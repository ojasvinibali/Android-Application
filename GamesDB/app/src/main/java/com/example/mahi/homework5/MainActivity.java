package com.example.mahi.homework5;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetGameListTask.Idata{

    ArrayList<Game> gamesList = new ArrayList<>();
    Integer id = 0;
    Game game = new Game();
    final static String GAME_ID = "game_id";
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go_button).setEnabled(false);
        findViewById(R.id.load_progress).setVisibility(View.GONE);

        radioGroup = (RadioGroup) findViewById(R.id.games_list);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                id = checkedId;
                findViewById(R.id.go_button).setEnabled(true);
            }
        });

        findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameDetails.class);
                intent.putExtra(GAME_ID,id);
                startActivity(intent);
            }
        });

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_text = (EditText) findViewById(R.id.search_input);
                radioGroup.removeAllViews();
                findViewById(R.id.load_progress).setVisibility(View.VISIBLE);
                new GetGameListTask(MainActivity.this).execute("http://thegamesdb.net/api/GetGamesList.php?name="+edit_text.getText());
            }
        });


    }

    @Override
    public void setupdata(ArrayList<Game> games){

        gamesList = games;
        radioGroup = (RadioGroup) findViewById(R.id.games_list);
        radioGroup.removeAllViews();
        String button_text = "";

        findViewById(R.id.load_progress).setVisibility(View.GONE);

        SimpleDateFormat informat = new SimpleDateFormat("mm/dd/yyyy");
        SimpleDateFormat outformat = new SimpleDateFormat("yyyy");


        for(int i=0;i<gamesList.size();i++)
        {
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setId(gamesList.get(i).getId());
            if(gamesList.get(i).getTitle()!= null)
            {
                button_text = button_text+gamesList.get(i).getTitle()+". ";
            }
            if(gamesList.get(i).getReleaseDate()!= null)
            {
                String date = null;
                try {
                    date = outformat.format(informat.parse(gamesList.get(i).getReleaseDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_text = button_text+"Released in "+date+". ";

            }
            if(gamesList.get(i).getPlatform()!=null)
            {
                button_text = button_text+"Platform: "+gamesList.get(i).getPlatform()+". ";
            }
            radioButton.setText(button_text);
            radioButton.setTextColor(Color.GRAY);
            radioGroup.addView(radioButton);
            button_text="";

        }

    }
}
