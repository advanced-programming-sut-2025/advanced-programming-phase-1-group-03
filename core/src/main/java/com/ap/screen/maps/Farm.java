package com.ap.screen.maps;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.audio.AudioService;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.managers.GameUIManager;
import com.ap.system.GrowSystem;
import com.ap.managers.MapManager;
import com.ap.managers.WeatherEffects;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.TimeSystem;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledMapGenerator;
import com.ap.tiled.TiledService;
import com.ap.ui.widget.Clock;
import com.ap.ui.widget.CraftingMenu;
import com.ap.ui.widget.InventoryMenu;
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

public class Farm implements IMap {
    private AssetService assetService;
    private AudioService audioService;
    private Engine engine;
    private TiledService tiledService;
    private TiledAshleyConfigurator tileConfigurator;
    private TiledMapGenerator tiledMapGenerator;
    private KeyboardController keyboardController;
    private World world;

    private Camera camera;
    private Viewport viewport;
    private Batch batch;

    private TimeSystem timeSystem;

    private TabManager tabManager;
    private ItemContainer itemContainer;
    private CraftingMenu craftingMenu;
    private Clock clock;

    // Use to for night darkness
    private RayHandler rayHandler;
    private TiledMap map;

    // For handling UI
    private Stage stage;

    private MapManager mapManager;
    private WeatherSystem weatherSystem;

    private WeatherEffects weatherEffects;

    private GameScreen gameScreen;
    private GdxGame game;
    private GrowSystem growSystem;

    public Farm(GdxGame game, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.game = game;

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

        timeSystem = gameScreen.getTimeSystem();
        weatherSystem = gameScreen.getWeatherSystem();

        weatherEffects = new WeatherEffects(assetService, engine, stage, audioService);

        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
    }

    @Override
    public void setup(MapAsset map) {
        // Adding systems to the engine
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, gameScreen));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new PlayerAudioSystem(assetService));
        engine.addSystem(new ScreenBrightnessSystem(rayHandler, engine, timeSystem, weatherSystem));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new SeasonalGraphicSystem(assetService, timeSystem));
        engine.addSystem(new AdjustAlphaSystem(engine));
        growSystem = new GrowSystem(assetService, weatherSystem);
        engine.addSystem(growSystem);
        engine.addSystem(new RenderSystem(batch, viewport, camera));
        // Actually it would be better we create a class for forest, but because of simplicity just hardcode it
        if(map == MapAsset.Farm1 || map == MapAsset.Farm2) {
            engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        }
        engine.addSystem(new ControllerSystem(inventoryMenu, craftingMenu, engine));
        engine.addSystem(new ControllerSystem(tabManager, engine));
        engine.addSystem(new PlayerCoinSystem(clock));

        timeSystem.addTimeListener(new TimeListener());

        // Setup consumers
        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = engine.getSystem(CameraSystem.class)::setMap;
        tiledService.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer));

        tiledService.setLoadTileConsumer(tileConfigurator::onLoadTile);
        tiledService.setLoadObjectConsumer(tileConfigurator::onLoadObject);
        tiledService.setBoundaryConsumer(tileConfigurator::onLoadBoundary);
        tiledService.setGenerateItemsConsumer(tiledMapGenerator::generate);
        tiledService.setLoadSpanwerConsumer(tileConfigurator::onLoadSpawner);
        TiledMap startMap = tiledService.load(map);
        this.map = startMap;
        tiledService.setMap(startMap);

    }

    @Override
    public void load() {
        game.setInputProcessors(stage, keyboardController);
        weatherSystem.setWeatherConsumer(weatherEffects::onWeatherChanged);
        engine.getSystem(CameraSystem.class).setMap(map);
        GameUIManager.instance.setEngine(engine);
    }

    @Override
    public void leave() {
        engine.getSystem(ControllerSystem.class).reset();
        weatherEffects.removeActors();
    }

    @Override
    public void update(float delta) {
        delta = Math.min(1 / 30f, delta);
        engine.update(delta);

        stage.act(delta);
        stage.draw();
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
    }

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
            tiledService.changeSeasonTileset(season);
        }
        @Override
        public void onDayChanged(int day) {
            growSystem.dayPassed();
        }
    }
}
