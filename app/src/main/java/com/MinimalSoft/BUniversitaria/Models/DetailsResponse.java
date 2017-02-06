package com.MinimalSoft.BUniversitaria.Models;

public class DetailsResponse {
    private String response;
    private String message;
    private AllPlaceData data;

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

    public AllPlaceData getData() {
        return data;
    }

    public void setData(AllPlaceData data) {
        this.data = data;
    }
}
