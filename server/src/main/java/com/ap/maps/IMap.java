package com.ap.maps;

import com.ap.asset.MapAsset;
import com.ap.requests.MovePlayerRequest;

public interface IMap {
    void update(float delta);
    void setup(MapAsset map);
    void load();
    void leave();
    void addSystems();

    int getEngineId();

    void movePlayer(MovePlayerRequest movePlayerRequest);

    void applyItem(int index, int x, int y);
}
