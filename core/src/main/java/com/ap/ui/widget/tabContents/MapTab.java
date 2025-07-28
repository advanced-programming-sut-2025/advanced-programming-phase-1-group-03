package com.ap.ui.widget.tabContents;

import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MapTab extends AbstractContent{
    public MapTab(Stage stage, AssetService assetService, Skin skin, AudioService audioService, int width, int height, Tabs icon) {
        super(stage, assetService, skin, audioService, width, height, icon);
        Label label = new Label("Map", skin);
        content.add(label);
    }
}
