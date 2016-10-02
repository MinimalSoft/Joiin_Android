package com.MinimalSoft.BrujulaUniversitaria.Web;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.MinimalSoft.BrujulaUniversitaria.R;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private LoadIndicator loadIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.options_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

        webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
        loadIndicator.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                finish();
                return true;

            case R.id.options_share:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}