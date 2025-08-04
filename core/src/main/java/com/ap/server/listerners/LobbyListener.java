package com.ap.server.listerners;

import com.ap.global.RoomInfo;
import com.ap.global.requests.CreateRoomRequest;
import com.ap.global.requests.IntroductionRequest;
import com.ap.global.requests.RoomsListRequest;
import com.ap.global.responses.IntroductionResponse;
import com.ap.global.responses.RoomsListResponse;
import com.ap.server.ServerData;
import com.ap.server.model.Room;
import com.ap.server.model.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        } else if(object instanceof CreateRoomRequest request) {
            var room = new Room();
            var senderPlayer = ServerData.instance.activePlayers.get(connection);

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
            ServerData.instance.activeRooms.add(room);
        }else if(object instanceof RoomsListRequest) {
            var response = new RoomsListResponse();
            List<RoomInfo> rooms = new ArrayList<>();
            for(Room room : ServerData.instance.activeRooms) {
                rooms.add(new RoomInfo(
                        room.id, room.isPrivate, room.players.size(),room.name, room.owner.name, room.owner.avatar, room.visible
                ));
            }
            response.roomsInfo = rooms.toArray(new RoomInfo[0]);
            connection.sendTCP(response);
        }
    }
}
