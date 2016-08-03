package com.MinimalSoft.BrujulaUniversitaria.Models;

/**
 * Created by David on 24/07/2016.
 */
public class Response_PlaceDetails {

    private String response;
    private String message;
    private Data_General data = new Data_General();

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

    public Data_General getData() {
        return data;
    }

    public void setData(Data_General data) {
        this.data = data;
    }

}
