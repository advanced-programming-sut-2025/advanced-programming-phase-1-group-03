package com.ap.notifiers;

import com.ap.requests.VoteRequest;

public class VoteNotifier {
    public int id;
    public String userName;
    public String senderUserName;
    public VoteRequest voteRequest;

    public VoteNotifier() {}

    public VoteNotifier(String userName, String senderUserName, int id, VoteRequest voteRequest) {
        this.id = id;
        this.userName = userName;
        this.senderUserName = senderUserName;
        this.voteRequest = voteRequest;
    }
}
