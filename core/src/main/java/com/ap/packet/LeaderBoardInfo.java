package com.ap.packet;

public class LeaderBoardInfo {
    public String playerName;
    public Integer gold;
    public Integer completeQuest;
    public Double skillNum;

    public LeaderBoardInfo() {}

    public LeaderBoardInfo(String playerName, Integer gold, Integer completeQuest, Double skillNum) {
        this.playerName = playerName;
        this.gold = gold;
        this.completeQuest = completeQuest;
        this.skillNum = skillNum;
    }
}
