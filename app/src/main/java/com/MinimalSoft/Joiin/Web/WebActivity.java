package com.MinimalSoft.Joiin.Web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.web_progress);
        WebView webView = (WebView) findViewById(R.id.web_webView);

        LoadIndicator loadIndicator = new LoadIndicator(progressBar, webView);
        toolbar.setTitle(getIntent().getStringExtra(Joiin.ACTIVITY_TITLE_KEY));
        String link = getIntent().getStringExtra(Joiin.WP_URL);

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