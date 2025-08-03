package com.ap.client.screen.maps;

import com.ap.client.asset.MapAsset;

public interface IMap {
    void update(float delta);
    void setup(MapAsset map);
    void load();
    void leave();
    void addSystems();
}
