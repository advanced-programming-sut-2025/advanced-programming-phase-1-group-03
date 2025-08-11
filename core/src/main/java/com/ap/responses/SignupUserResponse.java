package com.ap.responses;

public class SignupUserResponse {
    public String message;
    public boolean success;

    public SignupUserResponse() {}

    public SignupUserResponse( boolean success, String message) {
        this.message = message;
        this.success = success;
    }
}
