package com.MinimalSoft.BU.Models;

import java.util.ArrayList;
import java.util.List;

public class AllReviewsResponse {
    private String response;
    private Integer message;
    private List<ReviewsData> data = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public List<ReviewsData> getData() {
        return data;
    }

    public void setData(List<ReviewsData> data) {
        this.data = data;
    }
}