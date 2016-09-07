package com.MinimalSoft.BrujulaUniversitaria.Models;

public class ResponseRegister {
    private String response;
    private String message;
    private DataRegister data;

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

    public DataRegister getData() {
        return data;
    }

    public void setData(DataRegister data) {
        this.data = data;
    }
}