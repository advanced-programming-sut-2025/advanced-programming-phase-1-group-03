package com.ap.listerners;

import com.ap.ServerData;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.BuildGreenhouseMsgNotifier;
import com.ap.requests.ApplyItemRequest;
import com.ap.requests.BuildGreenhouseRequest;
import com.ap.requests.MovePlayerRequest;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        // Player is not authorized, so refuse to connect
        if(!ServerData.instance.activePlayers.containsKey(connection)){
            return;
        }
        ServerPlayer senderPlayer = ServerData.instance.activePlayers.get(connection);

        if(object instanceof MovePlayerRequest movePlayerRequest) {
            senderPlayer.playerManager.move(movePlayerRequest);
        } else if(object instanceof ApplyItemRequest applyItemRequest) {
            senderPlayer.playerManager.applyItem(applyItemRequest.index, applyItemRequest.x, applyItemRequest.y);
        } else if(object instanceof BuildGreenhouseRequest) {
            senderPlayer.playerManager.buildGreenhouse();
        }
    }
}
