package com.ap.client.network.listeners;

import com.ap.client.managers.GameUIManager;
import com.ap.client.ui.model.LobbyViewModel;
import com.ap.client.ui.view.LobbyView;
import com.ap.global.RoomInfo;
import com.ap.global.requests.RoomsListRequest;
import com.ap.global.responses.IntroductionResponse;
import com.ap.global.responses.RoomsListResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;

public class LobbyListener extends Listener {
    private LobbyViewModel lobbyViewModel;

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof IntroductionResponse) {
        } else if(object instanceof RoomsListResponse response) {
            ArrayList<LobbyView.ServerEntry> serverEntries = new ArrayList<>();
            for(RoomInfo info : response.roomsInfo) {
                serverEntries.add(new LobbyView.ServerEntry(
                        info.name, "avatar" + info.ownerAvatarIndex, info.currentPlayers
                ));
            }
            lobbyViewModel.setRooms(serverEntries);
        }
    }

    public void setLobbyViewModel(LobbyViewModel lobbyViewModel) {
        this.lobbyViewModel = lobbyViewModel;
    }
}
