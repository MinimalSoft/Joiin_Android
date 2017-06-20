package com.MinimalSoft.Joiin.Responses;

import java.util.List;

public class PromosResponse {
    private String response;
    private String message;
    private List<PromoData> data;

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public List<PromoData> getData() {
        return data;
    }
}