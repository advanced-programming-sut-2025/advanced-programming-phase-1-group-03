package com.ap.model;

import com.ap.asset.MapAsset;
import com.ap.managers.PlayerManager;
import com.ap.notifiers.SendGoldNotifier;
import com.badlogic.ashley.core.Engine;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    public String username;
    public Connection connection;
    public Room currentRoom;
    public int id = 0;
    public int gold = 1000;
    public int avatarIndex;

    public PlayerManager playerManager;
    public ServerPlayer(String username, Connection connection, int avatarIndex) {
        this.username = username;
        this.connection = connection;
        this.avatarIndex = avatarIndex;
    }

    public void advanceGold(int g) {
        this.gold += g;
        connection.sendTCP(new SendGoldNotifier(gold));
    }
}
