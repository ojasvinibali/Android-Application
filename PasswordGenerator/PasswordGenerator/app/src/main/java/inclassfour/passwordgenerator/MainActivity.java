package inclassfour.passwordgenerator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView txtViewThreadPwdCountValue;
    private TextView txtViewThreadPwdLengthValue;
    private TextView txtViewAsyncPwdCountValue;
    private TextView txtViewAsyncPwdLengthValue;
    private SeekBar skBarThreadPwdCount;
    private SeekBar skBarThreadPwdLength;
    private SeekBar skBarAsyncPwdCount;
    private SeekBar skBarAsyncPwdLength;
    private Button btnGenerate;
    private ProgressDialog progressDialog;

    private int pwdCountThread = 0;
    private int pwdLengthThread = 7;
    private int pwdCountAsync = 0;
    private int pwdLengthAsync = 7;
    ArrayList<String> PassThroughThread = new ArrayList<>();
    ArrayList<String> PassThroughAsync = new ArrayList<>();

    ExecutorService threadpool;
    Handler handler;
    ArrayList<String> passarraythread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewThreadPwdCountValue = (TextView)findViewById(R.id.thread_pwd_count);
        txtViewThreadPwdLengthValue = (TextView)findViewById(R.id.thread_pwd_length);
        txtViewAsyncPwdCountValue = (TextView)findViewById(R.id.async_pwd_count);
        txtViewAsyncPwdLengthValue = (TextView)findViewById(R.id.async_pwd_length);

        skBarThreadPwdCount = (SeekBar)findViewById(R.id.thread_pwd_count_seek);
        skBarThreadPwdLength = (SeekBar)findViewById(R.id.thread_pwd_length_seek);
        skBarAsyncPwdCount = (SeekBar)findViewById(R.id.async_pwd_count_seek);
        skBarAsyncPwdLength = (SeekBar)findViewById(R.id.async_pwd_length_seek);

        skBarThreadPwdCount.setMax(9);
        skBarThreadPwdCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdCountThread = progress + 1;
                txtViewThreadPwdCountValue.setText(String.valueOf(pwdCountThread));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        skBarThreadPwdLength.setMax(16);
        skBarThreadPwdLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdLengthThread = progress + 7;
                txtViewThreadPwdLengthValue.setText(String.valueOf(pwdLengthThread));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        skBarAsyncPwdCount.setMax(9);
        skBarAsyncPwdCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdCountAsync = progress + 1;
                txtViewAsyncPwdCountValue.setText(String.valueOf(pwdCountAsync));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        skBarAsyncPwdLength.setMax(16);
        skBarAsyncPwdLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdLengthAsync = progress + 7;
                txtViewAsyncPwdLengthValue.setText(String.valueOf(pwdLengthAsync));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        passarraythread= new ArrayList();

        btnGenerate = (Button)findViewById(R.id.generateBtn);
        btnGenerate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(pwdCountThread<1 || pwdCountThread>10 || pwdLengthThread<7 || pwdLengthThread > 23 || pwdCountAsync<1 || pwdCountAsync>10 || pwdLengthAsync<7 || pwdLengthAsync > 23){
                    Toast.makeText(getApplicationContext(), "Please set all values correctly!",Toast.LENGTH_LONG).show();
                }
                else {
                    threadpool= Executors.newFixedThreadPool(2);
                    threadpool.execute(new ThreadPwd(pwdCountThread, pwdLengthThread));
                    new AsyncPassword().execute(pwdCountAsync, pwdLengthAsync);
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMax(pwdCountAsync + pwdCountThread);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getResources().getString(R.string.generating_pwds_dialog));
                    progressDialog.show();
                    handler = new Handler(new Handler.Callback(){
                       @Override
                        public boolean handleMessage(Message msg) {
                            switch(msg.what) {
                                case ThreadPwd.STEP:
                                    progressDialog.setProgress(msg.getData().getInt("Progress"));
                                    break;
                                case ThreadPwd.DONE:
                                    ArrayList<String> threadFinal = (ArrayList)msg.obj;
                                    String a = "Thread";
                                    Validation(a, threadFinal);
                                    break;
                            }
                           return false;
                       }
                    });
                }
            }
        });
    }
    private class ThreadPwd implements Runnable {
        static final int START = 0x00;
        static final int STEP = 0x01;
        static final int DONE = 0x02;
        int count;
        int len;
        public ThreadPwd(int pwdCountThread, int pwdLengthThread) {
            this.count = pwdCountThread;
            this.len = pwdLengthThread;
        }

        @Override
        public void run() {
            String pwd;
            Message message;
            ArrayList<String> PassT = new ArrayList<>();

            for(int i=0; i<count; i++) {
                pwd = Util.getPassword(len);
                PassT.add(pwd);
                Log.d("Thread pwd", pwd);
                message = new Message();
                message.what = STEP;
                Bundle b = new Bundle();
                b.putInt("Progress",i+1);
                message.setData(b);
                handler.sendMessage(message);
            }
            message = new Message();
            message.what = DONE;
            message.obj = PassT;
            handler.sendMessage(message);
        }
    }

    public class AsyncPassword extends AsyncTask<Integer, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... params)
        {
            int count = params[0];
            int len = params[1];
            String pwd;
            ArrayList<String> PassA=new ArrayList<String>();
            for(int i=0;i<count;i++) {
                pwd = Util.getPassword(len);
                PassA.add(pwd);
                Log.d("Async pwd", pwd);
            }
            return PassA;
        }

        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            String a = "Async";
            Validation(a, s);

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(progressDialog.getProgress()+1);
        }
    }

    private void Validation(String a, ArrayList<String> s) {
        if (a == "Async"){
            PassThroughAsync=s;
        }
        if(a=="Thread"){
            PassThroughThread=s;
        }

        if(!(PassThroughThread.isEmpty())&&!(PassThroughAsync.isEmpty())){

            progressDialog.dismiss();
            Intent intent = new Intent(MainActivity.this,GeneratedPasswordsActivity.class);
            intent.putExtra("Thread_Pwds",PassThroughThread);
            intent.putExtra("Async_Pwds",PassThroughAsync);
            startActivity(intent);
        }
    }

}



