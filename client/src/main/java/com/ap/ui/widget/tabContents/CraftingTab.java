package com.ap.ui.widget.tabContents;

import com.ap.screen.GameScreen;
import com.ap.ui.widget.CraftingMenu;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CraftingTab extends AbstractContent{
    public CraftingTab(GameScreen gameScreen, int width, int height, Tabs icon) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), width, height, icon);
        this.gameScreen = gameScreen;
        makeStructure();
        loadData();
    }

    private final GameScreen gameScreen;
    private CraftingMenu menuManager;

    @Override
    public void makeStructure() {
        for (Actor child : getChildren()) {
            child.setVisible(false);
        }
        menuManager = gameScreen.getCraftingMenu();
    }

    @Override
    public void loadData() {

    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (menuManager.isShowing() != visible) {
            menuManager.toggle();
        }
    }

}
