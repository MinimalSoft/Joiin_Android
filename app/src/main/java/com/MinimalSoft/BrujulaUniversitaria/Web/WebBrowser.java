package com.MinimalSoft.BrujulaUniversitaria.Web;

import android.view.View;
import android.widget.Toast;
import android.widget.ProgressBar;

import android.graphics.Bitmap;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;

public class WebBrowser extends WebViewClient {
    private ProgressBar progressBar;
    private WebView webView;

    public WebBrowser(final WebView webView, final ProgressBar progressBar) {
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(this);

        progressBar.setIndeterminate(true);
        this.progressBar = progressBar;
        this.webView = webView;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        //Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
    }

    public void load (String link) {
        webView.loadUrl(link);
    }
}