package com.ap.server.listerners;

import com.ap.global.requests.IntroductionRequest;
import com.ap.global.responses.IntroductionResponse;
import com.ap.server.ServerData;
import com.ap.server.model.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof IntroductionRequest introductionRequest) {
            var player = new ServerPlayer();
            player.name = introductionRequest.name;
            player.avatar = introductionRequest.avatar;
            player.connection = connection;
            player.currentRoom = null;

            ServerData.instance.activePlayers.put(connection, player);

            var response = new IntroductionResponse();
            response.message = "Join successfully";
            response.success = true;

            connection.sendTCP(response);
        }
    }
}
