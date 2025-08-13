package com.ap.model;

import com.ap.packet.PlayerInfo;

import java.util.ArrayList;

public class TradeHistory {
    public PlayerInfo player1;
    public PlayerInfo player2;
    public ArrayList<NetworkItemStack> items1;
    public ArrayList<NetworkItemStack> items2;

    public TradeHistory() {
    }

    public TradeHistory(PlayerInfo player1, PlayerInfo player2, ArrayList<NetworkItemStack> items1, ArrayList<NetworkItemStack> items2) {
        this.player1 = player1;
        this.player2 = player2;
        this.items1 = items1;
        this.items2 = items2;
    }
}
