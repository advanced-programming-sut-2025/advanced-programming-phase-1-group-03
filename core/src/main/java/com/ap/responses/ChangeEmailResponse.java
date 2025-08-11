package com.ap.responses;

public class ChangeEmailResponse {
    public String message;
    public boolean success;

    public ChangeEmailResponse() {
    }

    public ChangeEmailResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
