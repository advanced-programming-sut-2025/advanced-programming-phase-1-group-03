package com.ap.network;

import com.ap.audio.VoiceChatClient;
import com.ap.network.listeners.GameListener;
import com.ap.network.listeners.JoinGameListener;
import com.ap.network.listeners.LobbyListener;
import com.ap.Configuration;
import com.ap.Registrator;
import com.ap.requests.IntroductionRequest;
import com.ap.requests.RoommatesInfoLobbyRequest;
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

        client = new Client(65536, 65536);

        // Registration
        Kryo kryo = client.getKryo();
        Registrator.register(kryo);

        // Add listeners
        listenersCache.put(LobbyListener.class, new LobbyListener());
        listenersCache.put(JoinGameListener.class, new JoinGameListener());
        listenersCache.put(GameListener.class, new GameListener());

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
            client.start();
            client.connect(Configuration.TIMEOUT, Configuration.HOST_IP, Configuration.TCP_PORT, Configuration.UDP_PORT);
        } catch (IOException ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    public Sender getSender() {
        return new Sender(client);
    }

    public void requestMyRoommatesInfo() {
        client.sendTCP(new RoommatesInfoLobbyRequest());
    }

    public Client getClient() {
        return client;
    }
}
