package com.ap.requests;

public class ChatRequest {
    public String message;

    // If it's null to means we sent public message
    public String toUser;

    public ChatRequest(String message, String toUser) {
        this.message = message;
        this.toUser = toUser;
    }

    public ChatRequest() {
    }
}
