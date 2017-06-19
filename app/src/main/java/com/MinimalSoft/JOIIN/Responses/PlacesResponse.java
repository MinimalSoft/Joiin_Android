package com.MinimalSoft.JOIIN.Responses;

import java.util.List;

public class PlacesResponse {
    private String response;
    private String message;
    private List<PlaceData> data;

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public List<PlaceData> getData() {
        return data;
    }
}