package com.ap.requests;

public class VoteRequest {
    public int id;
    public String userName;
    public int voteNum;

    public VoteRequest() {}

    public VoteRequest(String userName, int id, int voteNum) {
        this.id = id;
        this.userName = userName;
        this.voteNum = voteNum;
    }
}
