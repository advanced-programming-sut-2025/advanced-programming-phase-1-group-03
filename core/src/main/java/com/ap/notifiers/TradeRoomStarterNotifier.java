package com.ap.notifiers;

import com.ap.packet.TradeRoomStarter;

public class TradeRoomStarterNotifier {
    public int roomId;
    public TradeRoomStarter starter;

    public TradeRoomStarterNotifier() {
    }

    public TradeRoomStarterNotifier(int roomId, TradeRoomStarter starter) {
        this.roomId = roomId;
        this.starter = starter;
    }
}
