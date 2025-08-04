package com.ap.global;

public class RoomInfo {
    public int id;
    public boolean isPrivate;
    public int currentPlayers;
    public String name;
    public String ownerName;
    public boolean isVisible;
    public int ownerAvatarIndex;

    public RoomInfo() {}
    public RoomInfo(int id, boolean isPrivate, int currentPlayers,
                    String name, String ownerName, int ownerAvatarIndex, boolean isVisible) {
        this.id = id;
        this.isPrivate = isPrivate;
        this.currentPlayers = currentPlayers;
        this.name = name;
        this.ownerName = ownerName;
        this.ownerAvatarIndex = ownerAvatarIndex;
        this.isVisible = isVisible;
    }
}
