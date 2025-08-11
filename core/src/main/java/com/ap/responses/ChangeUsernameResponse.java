package com.ap.responses;

public class ChangeUsernameResponse {
    public String message;
    public boolean success;

    public ChangeUsernameResponse() {
    }
    public ChangeUsernameResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
