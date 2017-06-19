package com.MinimalSoft.JOIIN.Responses;

public class PlaceData {
    /* Data returned from details service. */
    private int id;
    private String placeName;
    private String street;
    private int number;
    private String neighborhood;
    private float latitude;
    private float longitude;
    private String image;
    private int phone1;
    private int ext1;
    private int phone2;
    private int ext2;
    private String email;
    private String webPage;
    private String facebook;
    private String twitter;
    private String instagram;
    private String description;

    /* Data returned from places service. */
    private int idPlace;
    private int idPackage;

    /*
     * places service returns an integer message.
     * details service returns a string message.
     */
    private Object stars;

    public int getId() {
        return id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }

    public int getPhone1() {
        return phone1;
    }

    public int getExt1() {
        return ext1;
    }

    public int getPhone2() {
        return phone2;
    }

    public int getExt2() {
        return ext2;
    }

    public String getEmail() {
        return email;
    }

    public String getWebPage() {
        return webPage;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getDescription() {
        return description;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public Object getStars() {
        return stars;
    }
}