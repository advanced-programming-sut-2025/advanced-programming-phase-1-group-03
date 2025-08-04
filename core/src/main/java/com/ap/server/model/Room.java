package com.ap.server.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public int id;
    public String name;
    public boolean isPrivate;
    public String password;
    public ServerPlayer owner;
    public boolean visible;

    public List<ServerPlayer> players = new ArrayList<>();

    public void broadcast(Object packet, ServerPlayer except) {
        for (ServerPlayer p : players) {
            if (p != except) {
                p.connection.sendTCP(packet);
            }
        }
    }
}
