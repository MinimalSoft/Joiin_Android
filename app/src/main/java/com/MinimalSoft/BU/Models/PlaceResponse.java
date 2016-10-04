package com.MinimalSoft.BU.Models;

import java.util.ArrayList;
import java.util.List;

public class PlaceResponse {
    private String response;
    private String message;
    private List<PlaceData> data = new ArrayList<>();

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

    public List<PlaceData> getData() {
        return data;
    }

    public void setData(List<PlaceData> data) {
        this.data = data;
    }
}
