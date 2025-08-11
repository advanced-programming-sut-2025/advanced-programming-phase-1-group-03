package com.ap.notifiers;

import com.ap.asset.MapAsset;

public class CreateMapNotifier {
    public int engineId;
    public MapAsset mapAsset;
    public boolean showWeather;
    public boolean tileSelectionSystem;

    public CreateMapNotifier(int engineId, MapAsset mapAsset, boolean showWeather, boolean tileSelectionSystem) {
        this.engineId = engineId;
        this.mapAsset = mapAsset;
        this.showWeather = showWeather;
        this.tileSelectionSystem = tileSelectionSystem;
    }

    public CreateMapNotifier() {
    }
}
