package com.ap.responses;

import com.ap.packet.LeaderBoardInfo;

import java.util.ArrayList;

public class LeaderBoardResponse {
    public ArrayList<LeaderBoardInfo> leaderBoardInfos;

    public LeaderBoardResponse() {}

    public LeaderBoardResponse(ArrayList<LeaderBoardInfo> leaderBoardInfos) {
        this.leaderBoardInfos = leaderBoardInfos;
    }
}
