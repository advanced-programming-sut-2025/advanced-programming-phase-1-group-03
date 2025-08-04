package com.ap.global.requests;

public class GetUserInfoRequest {
    public String token;
    public GetUserInfoRequest() {}
    public GetUserInfoRequest(String token) {
        this.token = token;
    }
}
