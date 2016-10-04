package com.MinimalSoft.BU.Models;

/**
 * Created by David on 24/07/2016.
 */
public class Response_Reviews {

    private String response;
    private String message;
    private Data_Reviews data = new Data_Reviews();

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

    //TODO: Make it return a list instead an object

    /*
    public List<Data_Reviews> getData() {
        return data;
    }

    public void setData(List<Data_Reviews> data) {
        this.data = data;
    }
*/
    public Data_Reviews getData() {
        return data;
    }

    public void setData(Data_Reviews data) {
        this.data = data;
    }

}
