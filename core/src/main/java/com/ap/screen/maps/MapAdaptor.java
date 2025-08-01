package com.ap.screen.maps;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.audio.AudioService;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.managers.GameUIManager;
import com.ap.managers.MapManager;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.TimeSystem;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledMapGenerator;
import com.ap.tiled.TiledService;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.ui.widget.Clock;
import com.ap.ui.widget.CraftingMenu;
import com.ap.ui.widget.ItemContainer;
import com.ap.ui.widget.tabContents.TabManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public abstract class MapAdaptor implements IMap {
    protected AssetService assetService;
    protected AudioService audioService;
    protected Engine engine;
    protected TiledService tiledService;
    protected TiledAshleyConfigurator tileConfigurator;
    protected TiledMapGenerator tiledMapGenerator;
    protected KeyboardController keyboardController;
    protected World world;

    protected Camera camera;
    protected Viewport viewport;
    protected Batch batch;

    protected TimeSystem timeSystem;

    protected TabManager tabManager;
    protected ItemContainer itemContainer;
    protected CraftingMenu craftingMenu;
    protected CheatCodeBox cheatCodeBox;
    protected Clock clock;

    // Use to for night darkness
    protected TiledMap map;
    protected MapAsset mapAsset;

    // For handling UI
    protected Stage stage;

    protected MapManager mapManager;
    protected WeatherSystem weatherSystem;

    protected GameScreen gameScreen;
    protected GdxGame game;

    public MapAdaptor(GdxGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        mapManager = gameScreen.getMapManger();
        stage = gameScreen.getStage();

        camera = game.getCamera();
        viewport = game.getViewport();
        batch = game.getBatch();

        engine = new Engine();

        // Setup world with zero gravity
        world = new World(Vector2.Zero, true);

        // Set autoClearForces to false because we want to apply our customized timeStep
        world.setAutoClearForces(false);

        keyboardController = new KeyboardController(GameControllerState.class, engine, gameScreen.getStage());

        assetService = game.getAssetService();
        audioService = game.getAudioService();

        tiledService = new TiledService(assetService);
        tileConfigurator = new TiledAshleyConfigurator(engine, world);
        tiledMapGenerator = new TiledMapGenerator(engine, assetService, world);

        // Setup inventory
        itemContainer = gameScreen.getItemContainer();
        craftingMenu = gameScreen.getCraftingMenu();
        tabManager = gameScreen.getTabManager();
        clock = gameScreen.getClock();
        cheatCodeBox = gameScreen.getCheatCodeBox();

        timeSystem = gameScreen.getTimeSystem();
        weatherSystem = gameScreen.getWeatherSystem();
    }

    protected void setupMap() {
        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        tiledService.setMapChangeConsumer(renderConsumer);

        tiledService.setLoadTileConsumer(tileConfigurator::onLoadTile);
        tiledService.setLoadObjectConsumer(tileConfigurator::onLoadObject);
        tiledService.setBoundaryConsumer(tileConfigurator::onLoadBoundary);
        tiledService.setGenerateItemsConsumer(tiledMapGenerator::generate);
        tiledService.setLoadTileDataConsumer(tileConfigurator::onLoadTileData);
        tiledService.setLoadMapConsumer(tileConfigurator::onLoadMap);
        tiledService.setMap(this.map);
    }
    public abstract void addSystems();

    @Override
    public void update(float delta) {
        delta = Math.min(1 / 30f, delta);
        engine.update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void setup(MapAsset map) {
        this.mapAsset = map;
        this.map = tiledService.load(map);
    }

    @Override
    public void load() {
        weatherSystem.setWeatherConsumer(null);
        game.setInputProcessors(stage, keyboardController);
        engine.getSystem(CameraSystem.class).setMap(map);
        GameUIManager.instance.setEngine(engine);
        gameScreen.setCurrentMap(mapAsset);
        gameScreen.setCurrentTiledMap(map);
    }

    @Override
    public void leave() {
        engine.getSystem(ControllerSystem.class).reset();

    }
}
