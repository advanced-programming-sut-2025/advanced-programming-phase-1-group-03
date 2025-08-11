package com.ap.requests;

public class ReactionRequest {
    public String message;
    public int emojiNum;

    public ReactionRequest() {
    }

    public ReactionRequest(String message) {
        this.message = message;
    }

    public ReactionRequest(int emojiNum) {
        this.emojiNum = emojiNum;
    }
}