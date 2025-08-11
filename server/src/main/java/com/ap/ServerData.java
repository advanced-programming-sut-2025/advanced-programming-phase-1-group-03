package com.ap;

import com.ap.model.Room;
import com.ap.model.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerData {
    public static ServerData instance = new ServerData();
    public final  Map<Connection, ServerPlayer> activePlayers = new HashMap<>();
    public final List<Room> activeRooms = new ArrayList<>();
}
