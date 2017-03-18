package com.example.ojasv.homework4;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {
    TextView questiontext , wrongoption,correctoption;
    TextView performanceText;
    LinearLayout container ;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ArrayList<Quiz> stats = (ArrayList<Quiz>) getIntent().getSerializableExtra("questions");
        HashMap<String,String> hashmap = (HashMap<String, String>) getIntent().getExtras().getSerializable("correct answers");
//        HashMap<String,String> wronganswer = new HashMap<>();
//        ArrayList<String> correctanswers = new ArrayList<>();
//
//        for(String f: hashmap.keySet()){
//              if(!(hashmap.get(f).toString().equals(stats.get(Integer.parseInt(f)).getAnswer().toString() ))){
//                 wronganswer.put(stats.get(Integer.parseInt(f)).getText().toString(),hashmap.get(f).toString());
//                  correctanswers.add(stats.get(Integer.parseInt(f)).getAnswer().toString());
//              }
//          }
        display(stats,hashmap);
        //Log.i("Wrong answers", String.valueOf(wronganswer));
    }

    private void display(ArrayList<Quiz> stats, HashMap<String,String> hashmap) {

        int noOfWAns = 0;
        for(Quiz quiz : stats) {
            if (hashmap.get(quiz.getId()).equals(-1+"") || !hashmap.get(quiz.getId()).equals(quiz.getAnswer())){
                noOfWAns++;
                LayoutInflater layoutinflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                container = (LinearLayout)findViewById(R.id.container);
                View AddView = layoutinflater.from(this).inflate(R.layout.row,null);
                questiontext =(TextView)AddView.findViewById(R.id.textViewQuestion);
                wrongoption =(TextView)AddView.findViewById(R.id.textViewYourAnswer);
                correctoption =(TextView)AddView.findViewById(R.id.textViewCorreectAnswer);
                questiontext.setText(quiz.getText());
                if (hashmap.get(quiz.getId()).equals(-1+"")){
                    //String wrngText = quiz.getChoices().get(Integer.parseInt(hashmap.get(quiz.getId()))-1);
                    wrongoption.setText("Your Answer: No option selected");
                }else{
                    String wrngText = quiz.getChoices().get(Integer.parseInt(hashmap.get(quiz.getId()))-1);
                    wrongoption.setText("Your Answer: "+wrngText);
                }
                String crctText = quiz.getChoices().get(Integer.parseInt(quiz.getAnswer())-1);
                correctoption.setText("Correct Answer: "+crctText);
                container.addView(AddView);
            }
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        float progress = 100 -(noOfWAns*100/16);
        progressBar.setProgress((int) progress);
        performanceText = (TextView)findViewById(R.id.textView6);
        performanceText.setText(progress+"%");

    }

    public void quitAction(View view) {
        finish();
    }

}
