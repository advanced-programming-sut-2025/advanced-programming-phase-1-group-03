package com.ap.responses;

public class JoinRoomResponse {
    public boolean success;
    public String message;


    public JoinRoomResponse() {
    }

    public JoinRoomResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
