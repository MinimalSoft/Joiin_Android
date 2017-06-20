package com.MinimalSoft.Joiin.Responses;

public class ReviewsData {
    private int idReview;
    private int idUser;
    private int idType;
    private int idPlace;
    private String placeName;
    private String name;
    private String text;
    private int stars;
    private int likes;
    private int dislikes;
    private int reaction;
    private String date;
    private String fbImage;
    private String image;

    public int getIdReview() {
        return idReview;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdType() {
        return idType;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getStars() {
        return stars;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getReaction() {
        return reaction;
    }

    public String getDate() {
        return date;
    }

    public String getFbImage() {
        return fbImage;
    }

    public String getImage() {
        return image;
    }
}