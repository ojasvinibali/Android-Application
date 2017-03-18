package com.example.mahi.homework5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class TrailerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_view);

        String videoURL = getIntent().getStringExtra(GameDetails.VIDEO_URL_KEY);
        String gameName = getIntent().getStringExtra(GameDetails.GAME_NAME_KEY);

        TextView textView = (TextView) findViewById(R.id.game_name);
        textView.setText(gameName+" trailer");

        /*<iframe width="560" height="315" src="https://www.youtube.com/embed/a4NT5iBFuZs" frameborder="0" allowfullscreen></iframe>*/

        WebView webView = (WebView) findViewById(R.id.trailer_view);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(videoURL);

    }
}
