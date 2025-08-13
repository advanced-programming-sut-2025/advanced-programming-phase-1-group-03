package com.ap.packet;

import com.ap.model.NetworkItemStack;

import java.util.ArrayList;

public class TradeRoomStarter {
    public PlayerInfo playerInfo1;
    public ArrayList<NetworkItemStack> inventory1;
    public int inv1MaxSize;
    public PlayerInfo playerInfo2;
    public ArrayList<NetworkItemStack> inventory2;
    public int inv2MaxSize;
    public int yourIndex;

    public TradeRoomStarter() {
    }

    public TradeRoomStarter(PlayerInfo playerInfo1, ArrayList<NetworkItemStack> inventory1, int inv1MaxSize, PlayerInfo playerInfo2, ArrayList<NetworkItemStack> inventory2, int inv2MaxSize) {
        this.playerInfo1 = playerInfo1;
        this.inventory1 = inventory1;
        this.inv1MaxSize = inv1MaxSize;
        this.playerInfo2 = playerInfo2;
        this.inventory2 = inventory2;
        this.inv2MaxSize = inv2MaxSize;
    }
}
