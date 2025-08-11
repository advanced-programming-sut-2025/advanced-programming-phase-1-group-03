package com.ap.requests;

import com.ap.asset.MapAsset;

public class SetMapRequest {
    public MapAsset map;

    public SetMapRequest() {
    }

    public SetMapRequest(MapAsset map) {
        this.map = map;
    }
}
