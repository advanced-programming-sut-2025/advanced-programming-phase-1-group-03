package com.ap.client.network;

import com.ap.client.database.SqliteConnection;
import com.ap.client.database.UserLoader;
import com.ap.client.model.GameData;
import com.ap.client.network.listeners.LobbyListener;
import com.ap.global.Configuration;
import com.ap.global.Registrator;
import com.ap.global.requests.IntroductionRequest;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class GameClient {
    private Client client;
    private SqliteConnection sqlite;

    public GameClient(SqliteConnection sqlite) {
        this.sqlite = sqlite;

        client = new Client();
        client.start();

        // Registration
        Kryo kryo = client.getKryo();
        Registrator.register(kryo);

        // Add listeners
        client.addListener(new LobbyListener());
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

    public void sendIntroduction() {
        var request = new IntroductionRequest();
        request.name = GameData.getInstance().getLoggedUserUsername();
        request.avatar = UserLoader.getLoggedInUserAvatarIndex(sqlite);
        client.sendTCP(request);
    }
}
