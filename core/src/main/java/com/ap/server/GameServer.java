package com.ap.server;

import com.ap.global.Configuration;
import com.ap.global.Registrator;
import com.ap.global.requests.IntroductionRequest;
import com.ap.global.responses.IntroductionResponse;
import com.ap.server.database.SqliteConnection;
import com.ap.server.listerners.LobbyListener;
import com.ap.server.model.ServerPlayer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {
    private Server server;

    public GameServer() {
        server = new Server();
    }
    public void start() throws Exception {
        server.start();
        server.bind(Configuration.TCP_PORT, Configuration.UDP_PORT);

        // Registrations
        Kryo kryo = server.getKryo();
        Registrator.register(kryo);

        SqliteConnection.instance.init();

        server.addListener(new LobbyListener());

        System.out.println("Server started successfully");
    }
}
