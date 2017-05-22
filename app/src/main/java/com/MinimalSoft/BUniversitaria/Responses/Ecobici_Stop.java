package com.MinimalSoft.BUniversitaria.Responses;

public class Ecobici_Stop {
    private String _id;
    private String empty_slots;
    private String free_bikes;
    private String totalBikes;
    private Ecobici_Stop_GeoData geo;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getEmptySlots() {
        return empty_slots;
    }

    public void setEmptySlots(String empty_slots) {
        this.empty_slots = empty_slots;
    }

    public String getFreeBikes() {
        return free_bikes;
    }

    public void setFreeBikes(String free_bikes) {
        this.free_bikes = free_bikes;
    }

    public String getTotalBikes() {
        return totalBikes;
    }

    public void setTotalBikes(String totalBikes) {
        this.totalBikes = totalBikes;
    }

    public Ecobici_Stop_GeoData getGeo() {
        return geo;
    }

    public void setGeo(Ecobici_Stop_GeoData geo) {
        this.geo = geo;
    }

}