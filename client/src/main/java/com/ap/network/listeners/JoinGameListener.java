package com.ap.network.listeners;

import com.ap.ui.model.JoiningViewModel;
import com.ap.ui.view.JoiningView;
import com.ap.model.RoommateLobbyInfo;
import com.ap.requests.RoommatesInfoLobbyRequest;
import com.ap.responses.RoommatesInfoLobbyResponse;
import com.ap.responses.StartGameResponse;
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
