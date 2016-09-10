package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import android.graphics.Bitmap;

public class Post {
    protected int ID;

    protected int userRating;
    protected int placeRating;

    protected String date;
    protected String review;
    protected String userName;
    protected String placeName;
    protected String placeAddress;

    protected Bitmap image;
    protected Bitmap thumbnail;

    public Post() {
    }
}