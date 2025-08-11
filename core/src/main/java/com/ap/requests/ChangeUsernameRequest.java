package com.ap.requests;

public class ChangeUsernameRequest {
    public String token;
    public String newUsername;

    public ChangeUsernameRequest() {
    }

    public ChangeUsernameRequest(String token, String newUsername) {
        this.token = token;
        this.newUsername = newUsername;
    }
}
