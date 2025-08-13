package com.ap.requests;

public class ReactionRequest {
    public String message;
    public Integer emojiNum;

    public ReactionRequest() {
    }

    public ReactionRequest(String message) {
        this.message = message;
    }

    public ReactionRequest(Integer emojiNum) {
        this.emojiNum = emojiNum;
    }
}