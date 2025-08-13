package com.ap.listerners;

import com.ap.ServerData;
import com.ap.managers.AbilityManager;
import com.ap.maps.Farm;
import com.ap.maps.Store;
import com.ap.model.AbilityType;
import com.ap.managers.GameManager;
import com.ap.model.ServerPlayer;
import com.ap.model.TradeRoom;
import com.ap.notifiers.*;
import com.ap.packet.LeaderBoardInfo;
import com.ap.packet.PlayerInfo;
import com.ap.packet.VoiceNetData;
import com.ap.requests.*;
import com.ap.responses.BuyItemResponse;
import com.ap.responses.ChatResponse;
import com.ap.responses.GetActiveTradeResponse;
import com.ap.responses.LeaderBoardResponse;
import com.ap.responses.RoommatesInfoResponse;
import com.ap.responses.*;
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
            senderPlayer.playerManager.move(movePlayerRequest, senderPlayer);
        } else if(object instanceof ApplyItemRequest applyItemRequest) {
            senderPlayer.playerManager.applyItem(applyItemRequest.index, applyItemRequest.x, applyItemRequest.y, senderPlayer);
        } else if(object instanceof BuildGreenhouseRequest) {
            senderPlayer.playerManager.buildGreenhouse(senderPlayer);
        } else if(object instanceof ReactionRequest reactionRequest) {
            if(reactionRequest.emojiNum == null)
                senderPlayer.playerManager.applyReaction(reactionRequest.message);
            else
                senderPlayer.playerManager.applyReaction(reactionRequest.emojiNum);
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
        } else if (object instanceof VoteRequest voteRequest) {
            if(voteRequest.voteNum == 1) {
                var notifier = new VoteNotifier(voteRequest.userName, senderPlayer.username, voteRequest.id, voteRequest);
                senderPlayer.currentRoom.broadcast(notifier, senderPlayer);
            }
            else if(voteRequest.voteNum > senderPlayer.currentRoom.players.size()) {
                    //TODO implement kicking player
            }
        } else if(object instanceof RoommatesInfoRequest) {
            List<ServerPlayer> players = senderPlayer.currentRoom.players;
            ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
            PlayerInfo yourInfo = new PlayerInfo(senderPlayer.username, senderPlayer.avatarIndex);
            for (ServerPlayer player : players) {
                playerInfos.add(new PlayerInfo(player.username, player.avatarIndex));
            }
            senderPlayer.connection.sendTCP(new RoommatesInfoResponse(playerInfos, yourInfo));
        } else if(object instanceof BuyItemRequest buyRequest) {
            var map = senderPlayer.currentRoom.game.getMapManager().currentMaps.get(senderPlayer);
            if(map instanceof Store store) {
                var result = store.buyItem(senderPlayer.id, buyRequest);
                senderPlayer.connection.sendTCP(result);
            } else {
                senderPlayer.connection.sendTCP(new BuyItemResponse(false, "You are not in the store"));
            }
        } else if(object instanceof PlaceCarrierRequest) {
            var map = senderPlayer.currentRoom.game.getMapManager().currentMaps.get(senderPlayer);
            if(map instanceof Farm farm) {
                farm.placeCarrier();
            }
        } else if (object instanceof TradeStartRequest tradeStartRequest) {
            var targetPlayer = senderPlayer.currentRoom.players.stream()
                    .filter((ServerPlayer p) -> p.username.equals(tradeStartRequest.targetUsername)).findFirst().orElse(null);
            if (targetPlayer == null) return;
            //check if req from target exist
            var fromReq = senderPlayer.playerManager.getActiveFromTradeRequests().stream().filter(
                    (ServerPlayer p) -> p.username.equals(tradeStartRequest.targetUsername)).findFirst().orElse(null);
            if (fromReq != null) {
                startTradeRoom(targetPlayer, senderPlayer);
                return;
            }
            var existedReq = senderPlayer.playerManager.getActiveToTradeRequests().stream().filter(
                    (ServerPlayer p) -> p.username.equals(tradeStartRequest.targetUsername)).findFirst().orElse(null);
            if (existedReq == null) {
                senderPlayer.playerManager.getActiveToTradeRequests().add(targetPlayer);
            }
            var existedReq2 = targetPlayer.playerManager.getActiveFromTradeRequests().stream().filter(
                    (ServerPlayer p) -> p.username.equals(senderPlayer.username)).findFirst().orElse(null);
            if (existedReq2 == null) {
                targetPlayer.playerManager.getActiveFromTradeRequests().add(senderPlayer);
            }
            targetPlayer.connection.sendTCP(new PopupNotifier(senderPlayer.username, "new trade request received"));
        } else if (object instanceof GetActiveTradeRequest getActiveTradeRequest) {
            ArrayList<PlayerInfo> from = new ArrayList<>();
            ArrayList<PlayerInfo> to = new ArrayList<>();
            for (ServerPlayer activeFromTradeRequest : senderPlayer.playerManager.getActiveFromTradeRequests()) {
                from.add(new PlayerInfo(activeFromTradeRequest.username, activeFromTradeRequest.avatarIndex));
            }
            for (ServerPlayer activeToTradeRequest : senderPlayer.playerManager.getActiveToTradeRequests()) {
                to.add(new PlayerInfo(activeToTradeRequest.username, activeToTradeRequest.avatarIndex));
            }
            var response = new GetActiveTradeResponse(from, to);
            senderPlayer.connection.sendTCP(response);
        } else if(object instanceof TradeCommandRequest command) {
            if (command.tradeRoomId < 0 ||
                    command.tradeRoomId >= senderPlayer.playerManager.getGameManager().getTradeRooms().size()) return;
            var room = senderPlayer.playerManager.getGameManager().getTradeRooms().get(command.tradeRoomId);
            if (!room.isPlayerExist(senderPlayer)) return;
            if (room.processReq(senderPlayer, command)) {
                senderPlayer.currentRoom.game.getTradeRooms().remove(room);
            }
        } else if(object instanceof TradeStarterReject reject) {
            var target = senderPlayer.currentRoom.players.stream().filter(
                    (ServerPlayer p) -> p.username.equals(reject.targetUsername)).findFirst().orElse(null);
            if (target == null) return;
            senderPlayer.playerManager.getActiveFromTradeRequests().remove(target);
            target.playerManager.getActiveToTradeRequests().remove(senderPlayer);
        } else if(object instanceof TradeStarterCancel cancel) {
            var target = senderPlayer.currentRoom.players.stream().filter(
                    (ServerPlayer p) -> p.username.equals(cancel.targetUsername)).findFirst().orElse(null);
            if (target == null) return;
            senderPlayer.playerManager.getActiveToTradeRequests().remove(target);
            target.playerManager.getActiveFromTradeRequests().remove(senderPlayer);
        } else if(object instanceof TradeHistoryRequest historyRequest) {
            senderPlayer.connection.sendTCP(new TradeHistoryResponse(senderPlayer.playerManager.getGameManager().getTradeHistory()));
        }
    }
    private void startTradeRoom(ServerPlayer player1, ServerPlayer player2) {
        GameManager gameManager = player1.playerManager.getGameManager();
        var existedRoom = gameManager.getTradeRooms().stream().filter(
                (TradeRoom t) -> t.isPlayerExist(player1) || t.isPlayerExist(player2)).findFirst().orElse(null);
        if (existedRoom != null) {
            player2.connection.sendTCP(new PopupNotifier(player1.username, "I'm trading right now!"));
            return;
        }
        player1.playerManager.getActiveToTradeRequests().remove(player2);
        player2.playerManager.getActiveFromTradeRequests().remove(player1);
        var room = new TradeRoom(player1.playerManager, player2.playerManager,gameManager.getTradeRooms().size());
        gameManager.getTradeRooms().add(room);
        var notifier = new TradeRoomStarterNotifier(room.getRoomId(), room.getStarter());
        notifier.starter.yourIndex = 0;
        player1.connection.sendTCP(notifier);
        notifier.starter.yourIndex = 1;
        player2.connection.sendTCP(notifier);
        var notifierEdit = new TradeCommandNotifier();
        notifierEdit.editing = true;
        var notifierWaiting = new TradeCommandNotifier();
        notifierWaiting.waiting = true;
        player1.connection.sendTCP(notifierEdit);
        player2.connection.sendTCP(notifierWaiting);
    }
}
