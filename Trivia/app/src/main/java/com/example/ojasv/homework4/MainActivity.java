package com.example.ojasv.homework4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetQuizAsyncTask.Idata {
      ProgressBar progressBar;
      Button exitTrivia;
      Button startTrivia;
      TextView loading;
      ImageView img;
    ArrayList<Quiz> questionresult = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String quizUrl = "http://dev.theappsdr.com/apis/trivia_json/index.php";

         new GetQuizAsyncTask(MainActivity.this).execute(quizUrl);
        Log.i("quiz in mainactivity",questionresult.toString());
         startTrivia = (Button)findViewById(R.id.buttonStartTrivia);
         progressBar = (ProgressBar)findViewById(R.id.progressBar);
         loading = (TextView)findViewById(R.id.textViewLoading);
         progressBar.setVisibility(View.VISIBLE);
         startTrivia.setEnabled(false);
         img = (ImageView)findViewById(R.id.imageView2);
         img.setVisibility(View.INVISIBLE);
         startTrivia.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.i("After click",questionresult.toString());
                 Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
                 intent.putExtra("DATA",questionresult);
                 startActivity(intent);
             }
         });


    }

    @Override
    public void setUpData(ArrayList<Quiz> quiz) {
       questionresult = quiz;
    }

    @Override
    public void showProgress() {
//   progressBar.setVisibility(View.VISIBLE);
        startTrivia =(Button)findViewById(R.id.buttonStartTrivia);
        startTrivia.setEnabled(false);
    }

    @Override
    public void stopProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        startTrivia.setEnabled(true);
        img.setVisibility(View.VISIBLE);
        loading.setText("Trivia is ready.");
    }
    public void quitAction(View view) {
        System.exit(0);
    }
}
