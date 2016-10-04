package com.MinimalSoft.BU.List;

public class Place {
    private int rating;
    private String name;
    private String address;
    private String imageURL;

    public Place(String name, String address, int rating, String imageURL) {
        this.imageURL = imageURL;
        this.address = address;
        this.rating = rating;
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
}