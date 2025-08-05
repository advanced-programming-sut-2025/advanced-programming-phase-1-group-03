package com.ap.global.requests;

public class ChangeEmailRequest {
    public String token;
    public String newEmail;

    public ChangeEmailRequest() {
    }

    public ChangeEmailRequest(String newEmail, String token) {
        this.newEmail = newEmail;
        this.token = token;
    }
}
