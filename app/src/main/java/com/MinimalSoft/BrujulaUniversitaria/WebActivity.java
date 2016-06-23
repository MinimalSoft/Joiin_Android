package com.MinimalSoft.BrujulaUniversitaria;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.web_toolbar);
        WebView webView = (WebView) this.findViewById(R.id.webView);

        String title = this.getIntent().getStringExtra("TITLE");
        String link = this.getIntent().getStringExtra("LINK");

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
        toolbar.setTitle(title);
        //Uri uri = Uri.parse("http://brujulauniversitaria.com.mx/2016/02/01/el-dilema-de-la-universidad/");
        //startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
