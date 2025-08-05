package com.ap.global.requests;

public class JoinRoomRequest {
    public int roomId;
    public String password;

    public JoinRoomRequest() {
    }

    public JoinRoomRequest(int roomId, String password) {
        this.roomId = roomId;
        this.password = password;
    }
}
