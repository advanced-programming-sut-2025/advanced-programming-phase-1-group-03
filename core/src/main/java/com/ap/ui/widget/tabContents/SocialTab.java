package com.ap.ui.widget.tabContents;

import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SocialTab extends AbstractContent{
    public SocialTab(Stage stage, AssetService assetService, Skin skin, AudioService audioService, int width, int height, Tabs icon) {
        super(stage, assetService, skin, audioService, width, height, icon);
        Label label = new Label("Social", skin);
        addActor(label);
    }

    @Override
    public void makeStructure() {

    }

    @Override
    public void loadData() {

    }
}
