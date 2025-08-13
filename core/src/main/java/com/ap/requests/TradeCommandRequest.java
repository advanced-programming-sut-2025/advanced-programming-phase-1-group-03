package com.ap.requests;

public class TradeCommandRequest {
    public int tradeRoomId;
    public int playerIndex; // to effect on
    public boolean isAddToBuff = false;
    public boolean isRemoveToBuff = false;
    public int itemIndex;

    public boolean acceptOffer = false;
    public boolean rejectOffer = false;
    public boolean quit = false;

    public boolean submitOffer = false;

    public TradeCommandRequest() {
    }

    public TradeCommandRequest(int tradeRoomId, int playerIndex, boolean isAddToBuff, boolean isRemoveToBuff, int itemIndex) {
        this.tradeRoomId = tradeRoomId;
        this.playerIndex = playerIndex;
        this.isAddToBuff = isAddToBuff;
        this.isRemoveToBuff = isRemoveToBuff;
        this.itemIndex = itemIndex;
    }
}
