package com.ap.server;

import com.ap.server.model.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;

public class ServerData {
    public static ServerData instance = new ServerData();
    public final Map<Connection, ServerPlayer> activePlayers = new HashMap<>();

}
