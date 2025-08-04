package com.ap.global.requests;

public class ChangePasswordRequest {
    public String username;
    public String newPassword;
    public String secQAns;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String username, String newPassword, String secQAns) {
        this.username = username;
        this.newPassword = newPassword;
        this.secQAns = secQAns;
    }
}
