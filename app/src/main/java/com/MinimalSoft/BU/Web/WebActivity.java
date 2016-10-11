package com.MinimalSoft.BU.Web;

import com.MinimalSoft.BU.R;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ProgressBar progressBar;
    private LoadIndicator loadIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        progressBar = (ProgressBar) findViewById(R.id.web_progress);
        webView = (WebView) findViewById(R.id.web_webView);

        loadIndicator = new LoadIndicator(progressBar, webView);
        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        String link = getIntent().getStringExtra("LINK");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(link);
        loadIndicator.execute();
    }

    /*----OnClickListener methods----*/

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}