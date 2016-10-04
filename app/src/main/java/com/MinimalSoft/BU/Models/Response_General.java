package com.MinimalSoft.BU.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 24/07/2016.
 */
public class Response_General {

    private String response;
    private String message;
    private List<Data_General> data = new ArrayList<>();

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

    public List<Data_General> getData() {
        return data;
    }

    public void setData(List<Data_General> data) {
        this.data = data;
    }

}
