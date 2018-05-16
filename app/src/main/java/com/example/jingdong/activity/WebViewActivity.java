package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jingdong.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());

        web_view.loadUrl(url);

    }

    private void initView() {
        web_view = (WebView) findViewById(R.id.web_view);
    }
}
