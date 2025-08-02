package com.ap.ui.widget.tabContents;

import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MapTab extends AbstractContent{
    public MapTab(GameScreen gameScreen, int width, int height, Tabs icon) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), width, height, icon);
        Label label = new Label("Map", skin);
        addActor(label);
    }

    @Override
    public void makeStructure() {

    }

    @Override
    public void loadData() {

    }
}
