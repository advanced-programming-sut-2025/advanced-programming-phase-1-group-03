package com.ap.server;

import com.ap.global.Configuration;
import com.ap.global.Registrator;
import com.ap.server.database.SqliteConnection;
import com.ap.server.listerners.AuthenticationListener;
import com.ap.server.listerners.LobbyListener;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class GameServer {
    private Server server;

    public GameServer() {
        server = new Server();
    }
    public void start() throws Exception {
        server.start();
        server.bind(Configuration.TCP_PORT);

        // Registrations
        Kryo kryo = server.getKryo();
        Registrator.register(kryo);

        SqliteConnection.instance.connect();

        server.addListener(new LobbyListener());
        server.addListener(new AuthenticationListener());

        System.out.println("Server started successfully");
    }
}
