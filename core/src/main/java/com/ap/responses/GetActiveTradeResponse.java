package com.ap.responses;

import com.ap.packet.PlayerInfo;

import java.util.ArrayList;

public class GetActiveTradeResponse {
    public ArrayList<PlayerInfo> from;
    public ArrayList<PlayerInfo> to;

    public GetActiveTradeResponse() {
    }

    public GetActiveTradeResponse(ArrayList<PlayerInfo> from, ArrayList<PlayerInfo> to) {
        this.from = from;
        this.to = to;
    }
}
