package com.ap.responses;

public class ChatResponse {
    public String message;
    public boolean success;

    public ChatResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ChatResponse() {
    }
}
