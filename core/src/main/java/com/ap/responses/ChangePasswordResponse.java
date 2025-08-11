package com.ap.responses;

public class ChangePasswordResponse {
    public String message;
    public boolean success;

    public ChangePasswordResponse() {
    }

    public ChangePasswordResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
