package com.ap.listerners;

import com.ap.ServerData;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ChatNotifier;
import com.ap.notifiers.PopupNotifier;
import com.ap.packet.VoiceNetData;
import com.ap.requests.ApplyItemRequest;
import com.ap.requests.BuildGreenhouseRequest;
import com.ap.requests.ChatRequest;
import com.ap.requests.MovePlayerRequest;
import com.ap.responses.ChatResponse;
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
        } else if(object instanceof ChatRequest chatRequest) {
            var notifier = new ChatNotifier(chatRequest.message, chatRequest.toUser != null, senderPlayer.username);
            if(chatRequest.toUser != null) {
                var targetPlayer = senderPlayer.currentRoom.players.stream()
                        .filter((ServerPlayer p) -> p.username.equals(chatRequest.toUser)).findFirst().orElse(null);
                if(targetPlayer == null) {
                    senderPlayer.connection.sendTCP(new ChatResponse("There is not player with that user name", false));
                    return;
                }
                targetPlayer.connection.sendTCP(notifier);
            } else {
                // Message is not private so send it to all players
                senderPlayer.currentRoom.broadcast(notifier, senderPlayer);
            }
            senderPlayer.connection.sendTCP(new ChatResponse("Message sent successfully", true));

            // Send popups
            for(ServerPlayer player : senderPlayer.currentRoom.players){
                if(player == senderPlayer) {
                    continue;
                }
                if(chatRequest.message.contains("@" + player.username)) {
                    player.connection.sendTCP(new PopupNotifier(senderPlayer.username, chatRequest.message));
                }
            }
        } else if(object instanceof VoiceNetData voicePacket) {
            senderPlayer.currentRoom.broadcastUDP(voicePacket, senderPlayer);
        }
    }
}
