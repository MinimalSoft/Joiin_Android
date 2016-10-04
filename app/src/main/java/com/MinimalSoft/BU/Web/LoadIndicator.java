package com.MinimalSoft.BU.Web;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class LoadIndicator extends AsyncTask {
    private int progress;
    private WebView webView;
    private ProgressBar progressBar;

    public LoadIndicator(final ProgressBar progressBar, final WebView webView) {
        this.progressBar = progressBar;
        this.webView = webView;
    }

    @Override
    protected void onPreExecute() {
        progress = 0;
        progressBar.setProgress(progress);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        do {
            publishProgress();
        } while (progress < 100);
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        progress = webView.getProgress();
        progressBar.setProgress(progress);
    }

    @Override
    protected void onPostExecute(Object o) {
        progressBar.setVisibility(ProgressBar.GONE);
    }
}