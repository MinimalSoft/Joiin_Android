package com.MinimalSoft.BU.RecyclerPosts;

public class Post {
    protected int userID;
    protected int postID;
    protected int typeID;
    protected int userRating;
    protected int likesCount;
    protected int dislikesCount;

    protected String review;
    protected String dateTime;
    protected String userName;
    protected String imageURL;
    protected String placeName;

    public Post(int userID, int postID, int typeID, int userRating, int likesCount, int dislikesCount, String userName, String placeName, String review, String dateTime, String imageURL) {
        this.dislikesCount = dislikesCount;
        this.likesCount = likesCount;
        this.userRating = userRating;
        this.placeName = placeName;
        this.userName = userName;
        this.dateTime = dateTime;
        this.imageURL = imageURL;
        this.review = review;
        this.typeID = typeID;
        this.postID = postID;
        this.userID = userID;
    }

    public Post() {
    }
}