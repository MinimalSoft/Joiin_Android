package com.MinimalSoft.BrujulaUniversitaria.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 24/07/2016.
 */
public class BU_Response {

    private String response;
    private String message;
    private List<BU_Response_Data> data = new ArrayList<BU_Response_Data>();

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

    public List<BU_Response_Data> getData() {
        return data;
    }

    public void setData(List<BU_Response_Data> data) {
        this.data = data;
    }

}
