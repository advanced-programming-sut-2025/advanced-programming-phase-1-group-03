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
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public class House implements IMap{
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

    private InventoryMenu inventoryMenu;
    private CraftingMenu craftingMenu;
    private Clock clock;
    private ItemContainer itemContainer;

    private TiledMap map;

    // For handling UI
    private Stage stage;

    private MapManager mapManager;
    private WeatherSystem weatherSystem;
    private GrowSystem growSystem;

    private WeatherEffects weatherEffects;

    private GdxGame game;
    private GameScreen gameScreen;

    public House(GdxGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        mapManager = gameScreen.getMapManger();
        stage = gameScreen.getStage();

        camera = game.getCamera();
        viewport = game.getViewport();
        batch = game.getBatch();

        itemContainer = gameScreen.getItemContainer();
        craftingMenu = gameScreen.getCraftingMenu();

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
        inventoryMenu = gameScreen.getInventoryMenu();
        clock = gameScreen.getClock();

        timeSystem = gameScreen.getTimeSystem();
        weatherSystem = gameScreen.getWeatherSystem();

        weatherEffects = new WeatherEffects(assetService, engine, stage, audioService);

        RayHandler.useDiffuseLight(true);
    }

    @Override
    public void setup(MapAsset map) {

        // Adding systems to the engine
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, gameScreen));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new CameraSystem(camera));
        growSystem = new GrowSystem(assetService, weatherSystem);
        engine.addSystem(growSystem);
        engine.addSystem(new RenderSystem(batch, viewport, camera));
        engine.addSystem(new ControllerSystem(inventoryMenu, craftingMenu, engine));
        engine.addSystem(new PlayerCoinSystem(clock));

        // It'd be better we create separate class for green house, but we hard code it :)
        if(map == MapAsset.Greenhouse) {
            engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        }
        //engine.addSystem(new PhysicDebugRenderSystem(camera, world));

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
        weatherSystem.setWeatherConsumer(null);
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
