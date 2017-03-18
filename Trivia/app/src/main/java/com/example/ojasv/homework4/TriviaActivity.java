package com.example.ojasv.homework4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class TriviaActivity extends AppCompatActivity implements GetImageQuizAsyncTask.IQuestion {
    ArrayList<Quiz> triviaquestion = new ArrayList<>();
    HashMap<String, String> answers = new HashMap<>();
    ArrayList<Quiz> countcorrect = new ArrayList<>();
    int item = 0;
    TextView questionText;
    private static CountDownTimer countDownTimer;
    private TextView txtViewTimeLeft;
    ImageView questionImage ;
    RadioGroup rg;
    ProgressBar progressBar;
    TextView questionID;
    GetImageQuizAsyncTask asynObj;
    int position = 0;
    Button previousbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Button nextbutton = (Button) findViewById(R.id.buttonNext);
        previousbutton = (Button) findViewById(R.id.buttonPrevious);

        txtViewTimeLeft = (TextView)findViewById(R.id.textView2);
        rg = (RadioGroup) findViewById(R.id.optionsradiogroup);
        questionImage = (ImageView)findViewById(R.id.imageView);
        questionID = (TextView) findViewById(R.id.textView);
        triviaquestion = (ArrayList<Quiz>) getIntent().getSerializableExtra("DATA");
        previousbutton.setEnabled(false);

        for (int i=0;i<triviaquestion.size();i++){
            answers.put(i+"",-1+"");
        }
        questionText = (TextView) findViewById(R.id.textView3);
        //Log.i("arraylist in trivia",triviaquestion.toString());
        Log.i("Count", String.valueOf(triviaquestion.get(0).getChoices().size()));
        display(position);
        countDownTimer = new CountDownTimer((120 * 1000), (1000)) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtViewTimeLeft.setText("Time Left: " + String.valueOf(millisUntilFinished/1000) + " Sec");
                //getResources().getString(R.string.text_view_time_left,
            }

            @Override
            public void onFinish() {
                loadStatsActivity();
            }
        }.start();
        progressBar = (ProgressBar) findViewById(R.id.progressBarImage);
        previousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item -= 1;
                display(item);
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    item += 1;
                    if(item < triviaquestion.size()){
                        display(item);
                    }
                    else
                        loadStatsActivity();
                }

        });
    }

    private void loadStatsActivity() {

            countDownTimer.cancel();
            Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
            intent.putExtra("questions", triviaquestion);
            intent.putExtra("correct answers", answers);
            startActivity(intent);
            finish();
        }

    public void display(final int position) {
        if (position==0)
            previousbutton.setEnabled(false);
        else
            previousbutton.setEnabled(true);

        if(!triviaquestion.get(position).getImage().toString().isEmpty()) {
            if(asynObj != null && !asynObj.isCancelled()) {
                asynObj.cancel(true);
            }
            questionImage.setImageBitmap(null);
            asynObj = new GetImageQuizAsyncTask(TriviaActivity.this);
            asynObj.execute(triviaquestion.get(position).getImage().toString());
            TextView noImage = (TextView)findViewById(R.id.noImageText);
            noImage.setText("");

        }
        else{
            questionImage.setImageBitmap(null);
            TextView noImage = (TextView)findViewById(R.id.noImageText);
            noImage.setText("No image for this question.");
        }
        int optionssize = triviaquestion.get(position).getChoices().size();
        questionText.setText(triviaquestion.get(position).getText().toString());
        int qNum = Integer.parseInt(triviaquestion.get(position).getId().toString())+1;
        questionID.setText("Q"+qNum);

        rg.removeAllViews();
        for (int i = 0; i < optionssize; i++) {
            RadioButton btn = new RadioButton(this);
            btn.setId(i);
            btn.setText(String.valueOf(triviaquestion.get(position).getChoices().get(i)));
            rg.addView(btn);

            if (!answers.isEmpty()) {

                if (answers.get(String.valueOf(position)) != null) {
                    int checkedRdButton = Integer.parseInt(answers.get(String.valueOf(position)));
                    if (i == checkedRdButton) {
                        Log.d("testing","option " + i);
                        //((RadioButton)rg.getChildAt(i)).setChecked(true);
                        int radio_button_Id = rg.getChildAt(i).getId();

                        rg.check( radio_button_Id );
                    }
                }

            }

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int j) {
                    j = j+1;
                    answers.put(triviaquestion.get(position).getId(), String.valueOf(j));
                    Log.i("Answers HM", String.valueOf(answers));
                }
            });
        }
    }
    @Override
    public void setupData(Bitmap image) {
       // if (this.item == questionIndex)
            questionImage.setImageBitmap(image);
    }



    @Override
    public void startProgress() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarImage);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgress() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarImage);
        progressBar.setVisibility(View.GONE);
    }
}





