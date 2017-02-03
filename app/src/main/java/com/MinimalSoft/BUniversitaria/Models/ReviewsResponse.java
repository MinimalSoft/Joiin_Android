package com.MinimalSoft.BUniversitaria.Models;

import java.util.ArrayList;
import java.util.List;

public class ReviewsResponse {
    private String response;
    private String message;
    private List<ReviewsData> data = new ArrayList<>();

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

    public List<ReviewsData> getData() {
        return data;
    }

    public void setData(List<ReviewsData> data) {
        this.data = data;
    }
}