package com.MinimalSoft.BUniversitaria.Responses;

import java.util.List;

public class ReviewsResponse {
    /*
     * reviews service returns an integer message.
     * getLatest service returns a string message.
     * putReview service does not return any data.
     */

    private Object message;
    private String response;
    private List<ReviewsData> data;

    public String getResponse() {
        return response;
    }

    public Object getMessage() {
        return message;
    }

    public List<ReviewsData> getData() {
        return data;
    }
}