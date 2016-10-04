package com.MinimalSoft.BU.RecyclerPosts;

public class Post {
    int userID;
    int postID;
    int typeID;
    int userRating;
    int likesCount;
    int dislikesCount;

    String review;
    String dateTime;
    String userName;
    String imageURL;
    String placeName;

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

    Post() {
    }
}