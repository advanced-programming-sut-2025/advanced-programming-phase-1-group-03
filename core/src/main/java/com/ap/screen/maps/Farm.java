package com.ap.screen.maps;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.items.Inventory;
import com.ap.managers.MapManager;
import com.ap.managers.WeatherManager;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.TimeSystem;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledMapGenerator;
import com.ap.tiled.TiledService;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.view.GameView;
import com.ap.ui.widget.Clock;
import com.ap.ui.widget.InventoryMenu;
import com.ap.ui.widget.ItemContainer;
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

    private Inventory inventory;

    private TimeSystem timeSystem;

    private InventoryMenu inventoryMenu;
    private ItemContainer itemContainer;
    private Clock clock;

    // Use to for night darkness
    private RayHandler rayHandler;
    private TiledMap map;

    // For handling UI
    private Stage stage;

    private MapManager mapManager;
    private WeatherSystem weatherSystem;

    private WeatherManager weatherManager;

    private GameScreen gameScreen;
    private GdxGame game;

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
        inventory = gameScreen.getInventory();
        itemContainer = gameScreen.getItemContainer();
        inventoryMenu = gameScreen.getInventoryMenu();
        clock = gameScreen.getClock();

        timeSystem = gameScreen.getTimeSystem();
        weatherSystem = gameScreen.getWeatherSystem();

        weatherManager = new WeatherManager(assetService, engine, stage, audioService);

        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
    }

    @Override
    public void setup(MapAsset map) {

        // Adding systems to the engine
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new PlayerAudioSystem(assetService));
        engine.addSystem(new ScreenBrightnessSystem(rayHandler, engine, timeSystem, weatherSystem));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new SeasonalGraphicSystem(assetService, timeSystem));
        engine.addSystem(new AdjustAlphaSystem(engine));
        engine.addSystem(new RenderSystem(batch, viewport, camera));
        engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        engine.addSystem(new ControllerSystem(inventoryMenu, engine));
        //engine.addSystem(new PhysicDebugRenderSystem(camera, world));

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
        weatherSystem.setWeatherConsumer(weatherManager::onWeatherChanged);
        engine.getSystem(CameraSystem.class).setMap(map);
    }

    @Override
    public void leave() {
        engine.getSystem(ControllerSystem.class).reset();
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
}
