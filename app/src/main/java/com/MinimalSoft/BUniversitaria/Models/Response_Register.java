package com.MinimalSoft.BUniversitaria.Models;

/**
 * Created by David on 24/07/2016.
 * Esta clase contiene todos los campos de todas las posibles respuestas
 * JSON del API
 */
public class Response_Register {

    private String response;
    private String message;
    private Integer data;

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

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
