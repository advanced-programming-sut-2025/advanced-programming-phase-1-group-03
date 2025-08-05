package com.ap.client.network.listeners;

import com.ap.client.ui.model.JoiningViewModel;
import com.ap.client.ui.view.JoiningView;
import com.ap.global.model.RoommateLobbyInfo;
import com.ap.global.requests.RoommatesInfoLobbyRequest;
import com.ap.global.responses.RoommatesInfoLobbyResponse;
import com.ap.global.responses.StartGameResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class JoinGameListener extends Listener {
    private JoiningViewModel joiningViewModel;

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof RoommatesInfoLobbyResponse response) {
            if(joiningViewModel == null) {
                return;
            }
            var players = joiningViewModel.getJoiningPlayers();
            players.clear();
            for(RoommateLobbyInfo player : response.roommates) {
                players.add(new JoiningView.Player(player.username, player.avatarIndex));
            }
            joiningViewModel.refresh();
        } else if(object instanceof StartGameResponse) {
            joiningViewModel.startGame();
        }
    }

    public void setJoiningViewModel(JoiningViewModel joiningViewModel) {
        this.joiningViewModel = joiningViewModel;
    }
}
