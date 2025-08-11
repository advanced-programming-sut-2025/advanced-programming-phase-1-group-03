package com.ap.responses;

public class GetUserInfoResponse {
    public boolean success;

    public String username;
    public int maximumCoin;
    public int gamesCount;
    public String email;
    public String nickname;
    public int avatarIndex;

    public GetUserInfoResponse() {
    }

    public GetUserInfoResponse(boolean success) {
        this.success = success;
    }

    public GetUserInfoResponse(boolean success, String username, int maximumCoin, int gamesCount, String email, String nickname, int avatarIndex) {
        this.success = success;
        this.username = username;
        this.maximumCoin = maximumCoin;
        this.gamesCount = gamesCount;
        this.email = email;
        this.nickname = nickname;
        this.avatarIndex = avatarIndex;
    }
}
