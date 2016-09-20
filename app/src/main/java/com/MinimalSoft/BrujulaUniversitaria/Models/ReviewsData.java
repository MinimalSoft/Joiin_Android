package com.MinimalSoft.BrujulaUniversitaria.Models;

public class ReviewsData {
    private String name;
    private String text;
    private Integer stars;
    private Integer likes;
    private Integer dislikes;
    private String date;
    private Object fbImage;
    private Integer idUser;
    private Object placeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getFbImage() {
        return fbImage;
    }

    public void setFbImage(Object fbImage) {
        this.fbImage = fbImage;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Object getPlaceName() {
        return placeName;
    }

    public void setPlaceName(Object placeName) {
        this.placeName = placeName;
    }
}