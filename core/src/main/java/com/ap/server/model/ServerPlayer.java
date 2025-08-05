package com.ap.server.model;

import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    public String username;
    public Connection connection;
    public Room currentRoom;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
    }
}
