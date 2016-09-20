package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import android.graphics.Bitmap;

public class Post {
    protected int ID;
    protected int userRating;
    protected int likesCount;
    protected int dislikesCount;

    protected String review;
    protected String dateTime;
    protected String userName;
    protected String placeName;
    protected Bitmap profileImage;

    public Post(int ID, int userRating, int likesCount, int dislikesCount, String userName, String placeName, String review, String dateTime, Bitmap profileImage) {
        this.dislikesCount = dislikesCount;
        this.profileImage = profileImage;
        this.likesCount = likesCount;
        this.userRating = userRating;
        this.placeName = placeName;
        this.userName = userName;
        this.dateTime = dateTime;
        this.review = review;
        this.ID = ID;
    }

    public Post() {
    }
}