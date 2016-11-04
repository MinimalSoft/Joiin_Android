package com.MinimalSoft.BUniversitaria.Models;

/**
 * Created by Jair-Jacobo on 12/08/2016.
 */
public class Response_Start {
    private String response;
    private String message;
    private Data_Start data;

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

    public Data_Start getData() {
        return data;
    }

    public void setData(Data_Start data) {
        this.data = data;
    }
}
