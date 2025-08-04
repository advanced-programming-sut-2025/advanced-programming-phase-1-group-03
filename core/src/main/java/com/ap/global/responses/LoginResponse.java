package com.ap.global.responses;

public class LoginResponse {
    public String message;
    public boolean success;
    public String token;

    public LoginResponse() {}

    public LoginResponse(String message, boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }
}
