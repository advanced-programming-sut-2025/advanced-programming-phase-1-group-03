package com.ap.client.network;

import com.ap.client.database.SqliteConnection;
import com.ap.client.database.UserLoader;
import com.ap.client.model.GameData;
import com.ap.client.network.listeners.LobbyListener;
import com.ap.global.Configuration;
import com.ap.global.Registrator;
import com.ap.global.requests.IntroductionRequest;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameClient {
    private final Client client;
    private final Map<Class<? extends Listener>, Listener> listenersCache = new HashMap<>();

    public GameClient() {

        client = new Client();
        client.start();

        // Registration
        Kryo kryo = client.getKryo();
        Registrator.register(kryo);

        // Add listeners
        listenersCache.put(LobbyListener.class, new LobbyListener());

        for(Listener listener : listenersCache.values()) {
            client.addListener(listener);
        }
    }

    public <T extends Listener> T getListener(Class<T> listenerClass) {
        return (T) listenersCache.get(listenerClass);
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

    public Sender getSender() {
        return new Sender(client);
    }
}
