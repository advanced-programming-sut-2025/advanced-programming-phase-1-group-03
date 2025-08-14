package com.ap.model;

public class FriendInfo {
    public String name;
    public int avatarIndex;
    public float friendship;

    public FriendInfo(String name, int avatarIndex, float friendship) {
        this.name = name;
        this.avatarIndex = avatarIndex;
        this.friendship = friendship;
    }

    public FriendInfo() {
    }
}
