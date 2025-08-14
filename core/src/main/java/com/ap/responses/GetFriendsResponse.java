package com.ap.responses;

import com.ap.model.FriendInfo;

public class GetFriendsResponse {
    public FriendInfo[] friends;
    public GetFriendsResponse(FriendInfo[] friends) {
        this.friends = friends;
    }
}
