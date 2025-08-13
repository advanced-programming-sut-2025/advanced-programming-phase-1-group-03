package com.ap.responses;

import com.ap.model.TradeHistory;

import java.util.ArrayList;

public class TradeHistoryResponse {
    public ArrayList<TradeHistory> tradeHistory;

    public TradeHistoryResponse() {
    }

    public TradeHistoryResponse(ArrayList<TradeHistory> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }
}
