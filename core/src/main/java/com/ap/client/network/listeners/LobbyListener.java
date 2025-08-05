package com.ap.client.network.listeners;

import com.ap.client.ui.model.LobbyViewModel;
import com.ap.client.ui.view.LobbyView;
import com.ap.global.RoomInfo;
import com.ap.global.responses.RoomsListResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;

public class LobbyListener extends Listener {
    private LobbyViewModel lobbyViewModel;

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof RoomsListResponse response) {
            ArrayList<LobbyView.ServerEntry> serverEntries = new ArrayList<>();
            for(RoomInfo info : response.roomsInfo) {
                if(!info.isVisible) {
                    continue;
                }
                serverEntries.add(new LobbyView.ServerEntry(
                        info.name, "avatar" + info.ownerAvatarIndex, info.currentPlayers, info.id, info.isPrivate
                ));
            }
            lobbyViewModel.setRooms(serverEntries);
        }
    }

    public void setLobbyViewModel(LobbyViewModel lobbyViewModel) {
        this.lobbyViewModel = lobbyViewModel;
    }
}
