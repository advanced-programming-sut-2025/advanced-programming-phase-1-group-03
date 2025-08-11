package com.ap.model;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Room {
    public int id;
    public String name;
    public boolean isPrivate;

    // If it's empty it means the room is public
    public String password;

    public ServerPlayer owner;
    public boolean visible;

    public List<ServerPlayer> players = new ArrayList<>();

    public GameManager game = null;

    public void broadcast(Consumer<Connection> consumer) {
        for(ServerPlayer player : players) {
            consumer.accept(player.connection);
        }
    }
    public void broadcast(Object packet) {
        for (ServerPlayer p : players) {
            p.connection.sendTCP(packet);
        }
    }
    public void broadcast(Object packet, ServerPlayer except) {
        for (ServerPlayer p : players) {
            if (p != except) {
                p.connection.sendTCP(packet);
            }
        }
    }
}
