package com.ap.global.responses;

import com.ap.global.model.RoommateLobbyInfo;

public class RoommatesInfoLobbyResponse {
    public RoommateLobbyInfo[] roommates;

    public RoommatesInfoLobbyResponse() {
    }

    public RoommatesInfoLobbyResponse(RoommateLobbyInfo[] roommates) {
        this.roommates = roommates;
    }
}
