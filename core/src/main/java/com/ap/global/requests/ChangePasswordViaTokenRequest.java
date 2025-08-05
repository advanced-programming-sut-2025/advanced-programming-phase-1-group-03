package com.ap.global.requests;

public class ChangePasswordViaTokenRequest {
    public String token;
    public String password;

    public ChangePasswordViaTokenRequest() {
    }

    public ChangePasswordViaTokenRequest(String token, String password) {
        this.token = token;
        this.password = password;
    }
}
