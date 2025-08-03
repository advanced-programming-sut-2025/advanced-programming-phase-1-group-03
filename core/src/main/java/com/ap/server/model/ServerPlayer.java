package com.ap.server.model;

import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    public String name;
    public Connection connection;
    public int avatar;
    public Room currentRoom;
}
