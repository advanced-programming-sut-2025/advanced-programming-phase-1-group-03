package com.ap.requests;

public class TradeStartRequest {
    //when player offer trade to player 2
    //player to with pop up notifier will know a trade request has been sent
    //in tradeStartMenu player 2 will see request
    //send accept or reject request
    //in case of accept server will send a TradeRoomStarter with TradeRoomStarterNotifier
    public String targetUsername;

    boolean isClose = false;

    public TradeStartRequest() {
    }

    public TradeStartRequest(String targetUsername) {
        this.targetUsername = targetUsername;
    }


}
