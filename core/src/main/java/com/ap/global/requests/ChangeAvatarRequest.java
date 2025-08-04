package com.ap.global.requests;

public class ChangeAvatarRequest {
    public String token;
    public int newAvatarIndex;

    public ChangeAvatarRequest() {
    }

    public ChangeAvatarRequest(String token, int newAvatarIndex) {
        this.token = token;
        this.newAvatarIndex = newAvatarIndex;
    }
}
