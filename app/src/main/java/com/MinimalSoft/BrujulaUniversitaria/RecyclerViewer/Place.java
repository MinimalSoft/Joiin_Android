package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Place implements Parcelable{

    private String idPlace;
    private String placeName;
    private String street;
    private String number;
    private String neighborhood;
    private String latitude;
    private String longitude;
    private String idPackage;
    private String stars;
    private String image;

    //This is to tell retrofit the real name of the field
    //@SerializedName("backdrop_path")

    public Place() {}

    protected Place(Parcel in) {
        idPlace = in.readString();
        placeName = in.readString();
        street = in.readString();
        number = in.readString();
        neighborhood = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        idPackage = in.readString();
        stars = in.readString();
        image = in.readString();

    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idPlace);
        parcel.writeString(placeName);
        parcel.writeString(street);
        parcel.writeString(number);
        parcel.writeString(neighborhood);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(idPackage);
        parcel.writeString(stars);
        parcel.writeString(image);
    }

    public static class PlaceResult {
        private List<Place> results;

        public List<Place> getResults() {
            return results;
        }
    }

}
