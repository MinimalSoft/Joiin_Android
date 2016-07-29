package com.MinimalSoft.BrujulaUniversitaria.Models;

/**
 * Created by David on 24/07/2016.
 */
public class BU_Response_Details {

    private String response;
    private String message;
    private BU_Response_Data data = new BU_Response_Data();

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

    public BU_Response_Data getData() {
        return data;
    }

    public void setData(BU_Response_Data data) {
        this.data = data;
    }

}
