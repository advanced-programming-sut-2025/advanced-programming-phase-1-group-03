package com.ap.requests;

public class ChangeNicknameRequest {
    public String token;
    public String newNickname;

    public ChangeNicknameRequest() {
    }

    public ChangeNicknameRequest(String token, String nickname) {
        this.token = token;
        this.newNickname = nickname;
    }
}
