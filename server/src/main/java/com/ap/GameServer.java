package com.ap;

import com.ap.Configuration;
import com.ap.Registrator;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.database.SqliteConnection;
import com.ap.items.EntityFactory;
import com.ap.items.ItemFactory;
import com.ap.listerners.AuthenticationListener;
import com.ap.listerners.GameListener;
import com.ap.listerners.LobbyListener;
import com.ap.model.Room;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import javax.swing.text.html.parser.Entity;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {
    private final AssetService assetService;

    private Server server;

    public GameServer() {
        server = new Server(65536, 65536);
        assetService = new AssetService(new InternalFileHandleResolver());
        EntityFactory.instance.setup(assetService);
        ItemFactory.instance.setAssetService(assetService);
        for(MapAsset map : MapAsset.values()){
            assetService.load(map);
        }
        for(AtlasAsset atlas : AtlasAsset.values()){
            assetService.load(atlas);
        }
    }
    public void start() throws Exception {
        server.start();
        server.bind(Configuration.TCP_PORT, Configuration.UDP_PORT);

        // Registrations
        Kryo kryo = server.getKryo();
        Registrator.register(kryo);

        SqliteConnection.instance.connect();

        server.addListener(new LobbyListener(assetService));
        server.addListener(new AuthenticationListener());
        server.addListener(new GameListener());

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::checkLobbyTimeout, 1, 1, TimeUnit.MINUTES);

        System.out.println("Server started successfully");
    }

    private void checkLobbyTimeout() {
        long now = System.currentTimeMillis();
        ServerData.instance.activeRooms.removeIf(room -> now - room.lastTimePlayerArrived > 5 * 60 * 1000);
    }
}
