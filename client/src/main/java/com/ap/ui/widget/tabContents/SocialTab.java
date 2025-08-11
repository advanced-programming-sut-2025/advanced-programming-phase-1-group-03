package com.ap.ui.widget.tabContents;

import com.ap.screen.GameScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class SocialTab extends AbstractContent{
    public SocialTab(GameScreen gameScreen, int width, int height, Tabs icon) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), width, height, icon);
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
