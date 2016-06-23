package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import android.graphics.Bitmap;
import android.widget.Button;

public class Article {
    private int ID;
    private String link;
    private String title;
    private Bitmap image;
    public Button button;

    public Article(String title, int id, Bitmap bitmap, String link) {
        this.image = bitmap;
        this.title = title;
        this.link = link;
        this.ID = id;
    }

    /*public void setID(int ID) {
        this.ID = ID;
    }*/

    public void setTitle (String title) {
        this.title = title;
    }

    public String getTitle () {
        return title;
    }

    public void setLink (String link) {
        this.link = link;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    /*public int getID() {
        return ID;
    }*/

    public String getLink () {
        return link;
    }

    public Bitmap getImage() {
        return image;
    }
}
