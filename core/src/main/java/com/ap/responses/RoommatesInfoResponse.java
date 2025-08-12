package com.ap.responses;

import com.ap.packet.PlayerInfo;

import java.util.ArrayList;

public class RoommatesInfoResponse {
    public ArrayList<PlayerInfo> players;

    public RoommatesInfoResponse() {
    }

    public RoommatesInfoResponse(ArrayList<PlayerInfo> players) {
        this.players = players;
    }
}
