package com.ap.requests;

public class LoginRequest {
    public String username;
    public String password;
    public boolean stayLoggedIn;

    public LoginRequest() {}
    public LoginRequest(String username, String password, boolean stayLoggedIn) {
        this.username = username;
        this.password = password;
        this.stayLoggedIn = stayLoggedIn;
    }
}
