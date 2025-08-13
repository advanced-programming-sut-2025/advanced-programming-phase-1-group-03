package com.ap.responses;

public class BuyItemResponse {
    public String message;
    public boolean success;

    public BuyItemResponse() {
    }

    public BuyItemResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }
}
