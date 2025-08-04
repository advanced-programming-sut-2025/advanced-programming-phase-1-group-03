package com.ap.global.requests;

public class SecurityQPassRequest {
    public String username;
    public String securityQuestionAnswer;
    public SecurityQPassRequest() {}
    public SecurityQPassRequest(String username, String securityQuestionAnswer) {
        this.username = username;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }
}
