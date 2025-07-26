package com.ap.screen.maps;

import com.ap.asset.MapAsset;

public interface IMap {
    void update(float delta);
    void setup(MapAsset map);
    void load();
}
