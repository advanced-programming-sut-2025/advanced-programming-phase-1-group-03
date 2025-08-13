package com.ap.responses;

import com.ap.packet.PlayerInfo;

import java.util.ArrayList;

public class RoommatesInfoResponse {
    public ArrayList<PlayerInfo> players;
    public PlayerInfo yourInfo;

    public RoommatesInfoResponse() {
    }

    public RoommatesInfoResponse(ArrayList<PlayerInfo> players, PlayerInfo yourInfo) {
        this.players = players;
        this.yourInfo = yourInfo;
    }
}
