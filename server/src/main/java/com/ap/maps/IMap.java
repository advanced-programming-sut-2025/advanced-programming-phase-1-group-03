package com.ap.maps;

import com.ap.asset.MapAsset;
import com.ap.model.ServerPlayer;
import com.ap.requests.MovePlayerRequest;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public interface IMap {
    void update(float delta);
    void setup(MapAsset map);
    void load(Entity player);
    Entity leave(ServerPlayer player);
    void addSystems();

    int getEngineId();

    void movePlayer(MovePlayerRequest movePlayerRequest, ServerPlayer senderPlayer);

    void applyItem(int index, int x, int y, int id);

    void addPlayer(ServerPlayer player, MapAsset map);

    Engine getEngine();

    Array<ServerPlayer> getPlayers();

    void hug(int tileX, int tileY, int id);

    void gift(int itemIndex, int id);
}
