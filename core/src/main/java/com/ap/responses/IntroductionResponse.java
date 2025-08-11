package com.ap.responses;

public class IntroductionResponse {
    public String message;
    public boolean success;

    public IntroductionResponse() {
    }

    public IntroductionResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }
}
