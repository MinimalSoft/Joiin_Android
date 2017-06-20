package com.MinimalSoft.Joiin.Responses;

public class TransactionResponse {
    private String response;
    private String message;
    private TransactionData data;

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public TransactionData getData() {
        return data;
    }
}