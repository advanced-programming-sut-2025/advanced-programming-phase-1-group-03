package com.ap.requests;

public class VoteRequest {
    public int id;
    public String userName;
    public String senderUserName;
    public int voteNum;

    public VoteRequest() {}

    public VoteRequest(String userName, String senderUserName, int id, int voteNum) {
        this.id = id;
        this.userName = userName;
        this.senderUserName = senderUserName;
        this.voteNum = voteNum;
    }
}
