package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewer;

/**
 * Created by David on 25/06/2016.
 */
public class PlacesRequest {
    private String action,idType,latitude,longitude,radio;

    public PlacesRequest(String action,String idType,String latitude,String longitude,String radio) {
        this.action = action;
        this.idType = idType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radio = radio;
    }
}