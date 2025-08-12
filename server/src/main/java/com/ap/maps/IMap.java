package com.ap.maps;

import com.ap.asset.MapAsset;
import com.ap.model.ServerPlayer;
import com.ap.requests.MovePlayerRequest;
import com.badlogic.ashley.core.Entity;

public interface IMap {
    void update(float delta);
    void setup(MapAsset map);
    void load(Entity player);
    Entity leave(ServerPlayer player);
    void addSystems();

    int getEngineId();

    void movePlayer(MovePlayerRequest movePlayerRequest, ServerPlayer senderPlayer);

    void applyItem(int index, int x, int y);

    void addPlayer(ServerPlayer player, MapAsset map);
}
