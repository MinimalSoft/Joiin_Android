package com.MinimalSoft.BrujulaUniversitaria.Web;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.MinimalSoft.BrujulaUniversitaria.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_web);

        WebView webView = (WebView) this.findViewById(R.id.webView);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.web_toolbar);
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.web_progress);

        WebBrowser webBrowser = new WebBrowser(webView, progressBar);
        String title = this.getIntent().getStringExtra("TITLE");
        String link = this.getIntent().getStringExtra("LINK");

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(title);
        webBrowser.load(link);
        this.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (!super.onOptionsItemSelected(item)) {
                    NavUtils.navigateUpFromSameTask(this);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}