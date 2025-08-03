package com.ap.client.network;

import com.ap.global.Configuration;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class GameClient {
    private Client client;
    public GameClient() {
        client = new Client();
        client.start();

        // Registration
        Kryo kryo = client.getKryo();

        // Add listeners

    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void tryingToConnect() {
        // If we already connect refuse to connect again
        if(client.isConnected()) {
            return;
        }
        try {
            client.connect(5000, Configuration.HOST_IP, Configuration.TCP_PORT, Configuration.UDP_PORT);
        } catch (IOException ignored) {
        }
    }
}
