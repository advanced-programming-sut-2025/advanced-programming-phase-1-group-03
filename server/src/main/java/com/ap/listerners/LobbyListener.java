package com.ap.listerners;

import com.ap.RoomInfo;
import com.ap.asset.AssetService;
import com.ap.model.RoommateLobbyInfo;
import com.ap.requests.*;
import com.ap.responses.*;
import com.ap.model.GameManager;
import com.ap.ServerData;
import com.ap.database.UserLoader;
import com.ap.model.Room;
import com.ap.model.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LobbyListener extends Listener {
    private final AssetService assetService;
    public LobbyListener(AssetService assetService) {
        this.assetService = assetService;
    }

    @Override
    public void received(Connection connection, Object object) {
        // Player is not authorized, so refuse to connect
        if(!ServerData.instance.activePlayers.containsKey(connection)){
            return;
        }
        ServerPlayer senderPlayer = ServerData.instance.activePlayers.get(connection);

        if(object instanceof CreateRoomRequest request) {
            var room = new Room();

            int[] chosenId = {0};
            do {
                chosenId[0] = new Random().nextInt(10000);
            }
            while(ServerData.instance.activeRooms.stream().anyMatch(r -> r.id == chosenId[0]));

            room.id = chosenId[0];

            room.name = request.name;
            room.password = request.password;
            room.isPrivate = !room.password.isEmpty();
            room.players.add(senderPlayer);
            room.owner = senderPlayer;
            room.visible = request.isVisible;
            senderPlayer.currentRoom = room;

            ServerData.instance.activeRooms.add(room);
        }else if(object instanceof RoomsListRequest) {
            var response = new RoomsListResponse();
            List<RoomInfo> rooms = new ArrayList<>();
            for(Room room : ServerData.instance.activeRooms) {
                int avatar = UserLoader.getUserAvatarIndex(room.owner.username);
                rooms.add(new RoomInfo(
                        room.id, room.isPrivate, room.players.size(),room.name, room.owner.username, avatar, room.visible
                ));
            }
            response.roomsInfo = rooms.toArray(new RoomInfo[0]);
            connection.sendTCP(response);
        } else if(object instanceof RoommatesInfoLobbyRequest) {
            sendRoommatesInfo(connection, senderPlayer.currentRoom);
        } else if(object instanceof JoinRoomRequest request) {
            Room room = ServerData.instance.activeRooms.stream().filter((Room r) -> r.id == request.roomId).findFirst().orElse(null);
            if(room == null) {
                var response = new JoinRoomResponse(false, "Hacker poofyooz");
                connection.sendTCP(response);
                return;
            }
            if(room.players.size() < 4 && room.password.equals(request.password)) {
                room.players.add(senderPlayer);
                senderPlayer.currentRoom = room;

                room.broadcast((Connection c) -> sendRoommatesInfo(c, room));
                connection.sendTCP(new JoinRoomResponse(true, "Joining..."));
            }
            if(room.players.size() == 4) {
                connection.sendTCP(new JoinRoomResponse(false, "Room is full"));
            } else {
                connection.sendTCP(new JoinRoomResponse(false, "Password is not correct"));
            }
        } else if(object instanceof AmIHostRequest) {
            var response = new AmIHostResponse(
                    senderPlayer.currentRoom != null && senderPlayer.currentRoom.owner.equals(senderPlayer)
            );
            connection.sendTCP(response);
        } else if(object instanceof StartGameRequest request) {
            boolean amIHost = senderPlayer.currentRoom != null && senderPlayer.currentRoom.owner.equals(senderPlayer);
            if(amIHost) {
                startGame(senderPlayer);
            }
        } else if(object instanceof SetMapRequest request) {
            var game = senderPlayer.currentRoom.game;
            senderPlayer.connection.sendTCP(new SetMapResponse());
            game.playerJoined(senderPlayer, request.map);
        }
    }

    private void startGame(ServerPlayer senderPlayer) {
        senderPlayer.currentRoom.broadcast(new StartGameResponse());
        senderPlayer.currentRoom.game = new GameManager(senderPlayer.currentRoom, assetService);
    }

    private static void sendRoommatesInfo(Connection connection, Room room) {
        List<RoommateLobbyInfo> roommates = new ArrayList<>();
        for(ServerPlayer player : room.players) {
            int avatar = UserLoader.getUserAvatarIndex(player.username);
            roommates.add(new RoommateLobbyInfo(player.username, avatar));
        }
        var response = new RoommatesInfoLobbyResponse(roommates.toArray(new RoommateLobbyInfo[0]));
        connection.sendTCP(response);
    }
}
