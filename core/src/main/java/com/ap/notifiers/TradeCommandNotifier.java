package com.ap.notifiers;

public class TradeCommandNotifier {
    public int playerIndex; // to effect on
    public boolean isAddToBuff = false;
    public boolean isRemoveToBuff = false;
    public int itemIndex;

    public boolean waiting = false;
    public boolean editing = false;
    public boolean deciding = false;

    public boolean yourTurn = false;

    public boolean close = false;

    public String message = "";

    public TradeCommandNotifier() {
    }

    public TradeCommandNotifier(int playerIndex, boolean isAddToBuff, boolean isRemoveToBuff, int itemIndex) {
        this.playerIndex = playerIndex;
        this.isAddToBuff = isAddToBuff;
        this.isRemoveToBuff = isRemoveToBuff;
        this.itemIndex = itemIndex;
    }

}
