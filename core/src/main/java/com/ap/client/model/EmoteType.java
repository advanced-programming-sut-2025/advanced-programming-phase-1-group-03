package com.ap.client.model;

public enum EmoteType {
    Opening(0),
    Closing(16),
    Heart(5),
    Noise(3),
    Sleep(6)
    ;
    public final int index;

    EmoteType(int index) {
        this.index = index;
    }

}
