package com.ap;

import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.database.SqliteConnection;
import com.ap.effect.TransitionManager;
import com.ap.items.Inventory;
import com.ap.screen.LoadingScreen;
import com.ap.system.universal.TimeSystem;
import com.ap.utils.PreferencesManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
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

    // Audio manger
    private AudioService audioService;

    // For handling inputs
    private InputMultiplexer inputMultiplexer;

    // Transition for changing screens
    private TransitionManager transitionManager;

    // Database
    private SqliteConnection sqlite;

    // We use preferences to store small data
    private PreferencesManager preferencesManager;

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        camera = new OrthographicCamera();

        viewport = new FitViewport(Constraints.WORLD_WIDTH, Constraints.WORLD_HEIGHT, camera);

        assetService = new AssetService(new InternalFileHandleResolver());

        batch = new SpriteBatch();

        audioService = new AudioService(assetService);

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        transitionManager = new TransitionManager(this);

        sqlite = new SqliteConnection();

        preferencesManager = new PreferencesManager();

        addScreen(new LoadingScreen(this));
        changeScreen(LoadingScreen.class);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        transitionManager.render(Gdx.graphics.getDeltaTime());
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

    public void changeScreen(Class<? extends Screen> screen) {
        Screen screenInstance = screenCache.get(screen);
        if(screenInstance == null) {
            throw new IllegalArgumentException("Screen does not exists");
        }
        setScreen(screenInstance);
    }

    public void setScreen(Class<? extends Screen> screen) {
        if(transitionManager.isTransitioning())
            return;
        Screen screenInstance = screenCache.get(screen);
        if(screenInstance == null) {
            throw new IllegalArgumentException("Screen does not exists");
        }
        transitionManager.initTransition(screen);
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

    public AudioService getAudioService() {
        return audioService;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setInputProcessors(InputProcessor... inputs) {
        inputMultiplexer.clear();
        for(InputProcessor inputProcessor : inputs) {
            inputMultiplexer.addProcessor(inputProcessor);
        }
    }

    public SqliteConnection getSqlite() {
        return sqlite;
    }

    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

}
