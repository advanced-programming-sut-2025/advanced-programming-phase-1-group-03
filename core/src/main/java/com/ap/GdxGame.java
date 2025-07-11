package com.ap;

import com.ap.asset.AssetService;
import com.ap.screen.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;
import java.util.Map;

public class GdxGame extends Game {

    // This viewport is for showing game not UI, and is being used in the whole program
    private FitViewport viewport;
    private OrthographicCamera camera;

    // We use one batch (because of memory efficiency) within the whole program
    private Batch batch;

    // Asset service is a singleton class
    private AssetService assetService;

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        viewport = new FitViewport(Constraints.WORLD_WIDTH, Constraints.WORLD_HEIGHT);
        camera = new OrthographicCamera();

        assetService = new AssetService(new InternalFileHandleResolver());

        batch = new SpriteBatch();

        addScreen(new LoadingScreen(this));
        setScreen(LoadingScreen.class);

    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.render();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void addScreen(Screen screen) {
        screenCache.put(screen.getClass(), screen);
    }

    public void removeScreen(Screen screen) {
        screenCache.remove(screen.getClass());
    }

    public void setScreen(Class<? extends Screen> screen) {
        Screen screenInstance = screenCache.get(screen);
        if(screenInstance == null) {
            throw new IllegalArgumentException("Screen does not exists");
        }
        setScreen(screenInstance);
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public AssetService getAssetService() {
        return assetService;
    }

    public Batch getBatch() {
        return batch;
    }
}
