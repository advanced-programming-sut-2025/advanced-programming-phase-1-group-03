package com.ap.listerners;

import com.ap.ServerData;
import com.ap.managers.AbilityManager;
import com.ap.model.AbilityType;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ChatNotifier;
import com.ap.notifiers.PopupNotifier;
import com.ap.packet.LeaderBoardInfo;
import com.ap.packet.VoiceNetData;
import com.ap.requests.*;
import com.ap.responses.ChatResponse;
import com.ap.responses.LeaderBoardResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;

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
        } else if(object instanceof ReactionRequest) {
            senderPlayer.playerManager.applyReaction();
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
        } else if(object instanceof LeaderBoardRequest) {
            System.out.println("salam man gereftam");
            List<ServerPlayer> players = senderPlayer.currentRoom.players;
            ArrayList<LeaderBoardInfo> leaderBoardInfos = new ArrayList<>();
            for(ServerPlayer serverPlayer : players) {
                AbilityManager abilityManager = serverPlayer.playerManager.getAbilityManager();
                double average = (double) (abilityManager.getAbility(AbilityType.Fishing).getLevel() +
                        abilityManager.getAbility(AbilityType.Farming).getLevel() +
                        abilityManager.getAbility(AbilityType.Foraging).getLevel() +
                        abilityManager.getAbility(AbilityType.Mining).getLevel()) / 4;
                // TODO when quests implemented complete here.
                // 1 is for test
                leaderBoardInfos.add(new LeaderBoardInfo(serverPlayer.username,
                        serverPlayer.gold, 1, average));
            }
            senderPlayer.connection.sendTCP(new LeaderBoardResponse(leaderBoardInfos));
        }
    }
}
