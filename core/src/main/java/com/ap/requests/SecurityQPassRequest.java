package com.ap.requests;

public class SecurityQPassRequest {
    public String username;
    public String securityQuestionAnswer;
    public SecurityQPassRequest() {}
    public SecurityQPassRequest(String username, String securityQuestionAnswer) {
        this.username = username;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }
}
