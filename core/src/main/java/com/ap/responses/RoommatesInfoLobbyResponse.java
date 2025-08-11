package com.ap.responses;

import com.ap.model.RoommateLobbyInfo;

public class RoommatesInfoLobbyResponse {
    public RoommateLobbyInfo[] roommates;

    public RoommatesInfoLobbyResponse() {
    }

    public RoommatesInfoLobbyResponse(RoommateLobbyInfo[] roommates) {
        this.roommates = roommates;
    }
}
