package com.ap.packet;

import com.ap.model.NetworkItemStack;

import java.util.ArrayList;

public class TradeRoomStarter {
    public PlayerInfo playerInfo1;
    public ArrayList<NetworkItemStack> inventory1;
    public PlayerInfo playerInfo2;
    public ArrayList<NetworkItemStack> inventory2;

    public TradeRoomStarter() {
    }

    public TradeRoomStarter(PlayerInfo playerInfo1, ArrayList<NetworkItemStack> inventory1, PlayerInfo playerInfo2, ArrayList<NetworkItemStack> inventory2) {
        this.playerInfo1 = playerInfo1;
        this.inventory1 = inventory1;
        this.playerInfo2 = playerInfo2;
        this.inventory2 = inventory2;
    }
}
