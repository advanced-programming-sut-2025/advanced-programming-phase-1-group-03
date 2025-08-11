package com.ap.responses;

public class ChangePasswordViaTokenResponse {
    public String message;
    public boolean success;

    public ChangePasswordViaTokenResponse() {
    }

    public ChangePasswordViaTokenResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
