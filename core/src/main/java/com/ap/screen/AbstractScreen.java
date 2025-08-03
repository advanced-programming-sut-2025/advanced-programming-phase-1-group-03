package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.SkinAsset;
import com.ap.Constraints;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;

import javax.swing.plaf.IconUIResource;

public abstract class AbstractScreen implements Screen {

    protected final FitViewport uiViewport;
    protected final Stage stage;
    protected final Skin skin;
    protected final GdxGame game;

    public AbstractScreen(GdxGame game) {
        this.game = game;
        uiViewport = new FitViewport(Constraints.WORLD_WIDTH_RESOLUTION, Constraints.WORLD_HEIGHT_RESOLUTION);
        stage = new Stage(uiViewport, game.getBatch());

        skin = game.getAssetService().get(SkinAsset.Default);
//        for (ObjectMap.Entry<String, BitmapFont> entry : skin.getAll(BitmapFont.class)) {
//            entry.value.getData().markupEnabled = true;
//            System.out.println(entry);
//        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        uiViewport.apply();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return  stage;
    }
}
