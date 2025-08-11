package com.ap.requests;

public class MovePlayerRequest {
    public float dx;
    public float dy;
    public boolean isKeyDown;

    public MovePlayerRequest() {
    }

    public MovePlayerRequest(float dx, float dy, boolean isKeyDown) {
        this.dx = dx;
        this.dy = dy;
        this.isKeyDown = isKeyDown;
    }
}
