package com.MinimalSoft.BUniversitaria.RecyclerArticles;

import android.graphics.Bitmap;

public class Article {
    protected int ID;
    protected String url;
    protected String link;
    protected String title;
    protected Bitmap image;

    Article(int ID, String url, String link, String title) {
        this.title = title;
        this.link = link;
        this.url = url;
        this.ID = ID;
    }

    Article() {
    }
}