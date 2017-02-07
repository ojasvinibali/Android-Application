package homework3.wordfrequency;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText numberOne;
    ImageButton addEditTextBtn;
    LinearLayout container;
    EditText rowEditText;
    CheckBox matchCases;
    ArrayList<String> wordsToSearch;
    Button searchBtn;
    private ProgressDialog progressDialog;
    ArrayList<String> PassThroughToNext = new ArrayList<>();
    ExecutorService threadpool;
    Handler handler;
    ArrayList<String> finList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOne = (EditText)findViewById(R.id.wordText);
        final String numberOneText = numberOne.getText().toString().trim();
        addEditTextBtn = (ImageButton)findViewById(R.id.addNew);
        matchCases = (CheckBox)findViewById(R.id.checkBox);
        container = (LinearLayout)findViewById(R.id.container);
        wordsToSearch = new ArrayList<>();
        searchBtn = (Button)findViewById(R.id.search_btn);
        threadpool = Executors.newFixedThreadPool(2);
        addEditTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOne.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill up first box.",Toast.LENGTH_LONG).show();
                }
                else if(wordsToSearch.size()>20) {
                    Toast.makeText(getApplicationContext(), "20 words limit reached.", Toast.LENGTH_LONG).show();
                }
                else{
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View AddView = layoutInflater.inflate(R.layout.row, null);
                    ImageButton removeEditTextBtn = (ImageButton)AddView.findViewById(R.id.removeThis);
                    rowEditText = (EditText)AddView.findViewById(R.id.rowEditText);
                    wordsToSearch.add(numberOne.getText().toString().trim());
                    rowEditText.setText(numberOne.getText().toString().trim());
                    numberOne.setText("");
                    Log.i("words", String.valueOf(wordsToSearch));
                    final View.OnClickListener thisBtnListener = new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)AddView.getParent()).removeView(AddView);
                            EditText removal = (EditText)AddView.findViewById(R.id.rowEditText);
                            wordsToSearch.remove(removal.getText().toString().trim());
                            Log.i("List after removal", String.valueOf(wordsToSearch));
                        }
                    };
                    removeEditTextBtn.setOnClickListener(thisBtnListener);
                    container.addView(AddView);
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(wordsToSearch.size()<1) {
                    Toast.makeText(getApplicationContext(), "Enter at least one word to search.", Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isChecked = matchCases.isChecked();
                    threadpool.execute(new WordCountThread(wordsToSearch, isChecked));
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMax(wordsToSearch.size());
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getResources().getString(R.string.searching_file_dialog));
                    progressDialog.show();
                    handler = new Handler(new Handler.Callback(){
                        @Override
                        public boolean handleMessage(Message msg) {
                            switch(msg.what) {
                                case WordCountThread.STEP:
                                    progressDialog.setProgress(msg.getData().getInt("Progress"));
                                    break;
                                case WordCountThread.DONE:
                                    HashMap<String, Integer> threadFinal = (HashMap)msg.obj;
                                    ValidateMap(threadFinal);
                                    break;
                            }
                            return false;
                        }
                    });
             }
            }
        });
    }

    private class WordCountThread implements Runnable {
        static final int STEP = 0x00;
        static final int DONE = 0x01;
        ArrayList words;
        boolean checked;

        public WordCountThread(ArrayList<String> wordsToSearch, boolean isChecked) {
            this.words = wordsToSearch;
            this.checked = isChecked;
        }

        public void run() {
            int count = 0;
            HashMap<String, Integer> hM = new HashMap<>();
            Message message;
             if (checked == true) {
                for(int i=0; i<words.size(); i++) {
                    String fileBuffer = null;
                    try {
                        fileBuffer = readFile("largetextfile.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Pattern p = Pattern.compile(" "+words.get(i)+" ");
                    Matcher m = p.matcher(fileBuffer);

                    while(m.find()) {
                        count++;
                    }
                    hM.put((String) words.get(i),count);
                    message = new Message();
                    message.what = STEP;
                    Bundle b = new Bundle();
                    b.putInt("Progress",i+1);
                    message.setData(b);
                    handler.dispatchMessage(message);
                }
            }
            else if (checked == false) {
                for(int i=0; i<words.size(); i++) {
                    String fileBuffer = null;
                    try {
                        fileBuffer = readFile("largetextfile.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String regex = "\\W*((?i)"+words.get(i)+"(?-i))\\W*";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(fileBuffer);

                    while(m.find()) {
                        count++;
                    }
                    hM.put((String) words.get(i),count);
                    message = new Message();
                    message.what = STEP;
                    Bundle b = new Bundle();
                    b.putInt("Progress",i+1);
                    message.setData(b);
                    handler.dispatchMessage(message);
                }
            }
            message = new Message();
            message.what = DONE;
            message.obj = hM;
            handler.dispatchMessage(message);
        }
        private String readFile(String s) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(s)));
            StringBuilder sb = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine);
                mLine = reader.readLine();
            }
            reader.close();
            return sb.toString();
        }
    }

    private void ValidateMap(HashMap<String, Integer> threadFinal) {
        progressDialog.dismiss();
        for(Map.Entry<String, Integer> e: threadFinal.entrySet()){
            PassThroughToNext.add(e.getKey()+": "+e.getValue());
        }
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("resultArrayList",PassThroughToNext);
        startActivity(intent);
    }
    //exit app
    @Override
    public void onBackPressed() {
        finish();
    }

}
