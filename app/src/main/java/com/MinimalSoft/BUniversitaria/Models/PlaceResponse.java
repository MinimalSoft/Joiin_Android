package com.MinimalSoft.BUniversitaria.Models;

public class PlaceResponse {
    private String response;
    private String message;
    private PlaceData data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PlaceData getData() {
        return data;
    }

    public void setData(PlaceData data) {
        this.data = data;
    }
}
