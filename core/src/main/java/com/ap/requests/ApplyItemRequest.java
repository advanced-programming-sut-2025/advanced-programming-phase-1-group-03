package com.ap.requests;

public class ApplyItemRequest {
    public int index;
    public int x;
    public int y;

    public ApplyItemRequest() {
    }

    public ApplyItemRequest(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }
}
