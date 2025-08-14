package com.ap.maps;


import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.managers.MapManager;
import com.ap.managers.GameManager;
import com.ap.model.ServerPlayer;
import com.ap.requests.MovePlayerRequest;
import com.ap.system.PhysicMoveSystem;
import com.ap.system.universal.TimeSystem;
import com.ap.system.universal.WeatherSystem;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledMapGenerator;
import com.ap.tiled.TiledService;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class MapAdaptor implements IMap {
    protected Engine engine;
    protected TiledService tiledService;
    protected TiledAshleyConfigurator tileConfigurator;
    protected TiledMapGenerator tiledMapGenerator;
    protected World world;

    protected AssetService assetService;

    protected TimeSystem timeSystem;

    protected TiledMap map;
    protected MapAsset mapAsset;

    protected WeatherSystem weatherSystem;

    protected GameManager gameManager;

    protected Array<ServerPlayer> players = new Array<>();

    protected MapManager mapManager;

    protected int playerId;

    public MapAdaptor(GameManager gameManager, MapManager mapManager, int playerId) {
        this.playerId = playerId;
        this.gameManager = gameManager;
        this.mapManager = mapManager;

        engine = new Engine();
        Helper.createIdForEngine(engine);

        assetService = gameManager.getAssetService();

        Box2D.init();

        // Setup world with zero gravity
        world = new World(Vector2.Zero, true);

        // Set autoClearForces to false because we want to apply our customized timeStep
        world.setAutoClearForces(false);

        tiledService = new TiledService(assetService);
        tileConfigurator = new TiledAshleyConfigurator(engine, world, playerId);
        tiledMapGenerator = new TiledMapGenerator(engine, assetService, world);

        // Setup inventory
        timeSystem = gameManager.getTimeSystem();
        weatherSystem = gameManager.getWeatherSystem();
    }

    protected void setupMap() {
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
        engine.update(delta);
    }

    @Override
    public void setup(MapAsset map) {
        this.mapAsset = map;
        this.map = tiledService.load(map);
    }

    @Override
    public void load(Entity player) {
    }
    @Override
    public Entity leave(ServerPlayer player) {
        engine.getSystem(PhysicMoveSystem.class).stopPlayer(player);
        return Helper.getPlayer(engine, player.id);
    }

    @Override
    public int getEngineId() {
        return Helper.getEngineId(engine);
    }

    public void movePlayer(MovePlayerRequest request, ServerPlayer senderPlayer) {
        engine.getSystem(PhysicMoveSystem.class).movePlayer(request, senderPlayer);
    }


    @Override
    public void applyItem(int index, int x, int y, int id) {
        var player = getPlayer(id);
        if(player != null) {
            player.playerManager.getInventory().getItems().get(index).getItem().applyItem(
                    Helper.getTopBodyAtPoint(new Vector2(x, y), world, map),
                    engine,
                    player.playerManager,
                    world
            );
        }
    }

    private ServerPlayer getPlayer(int id) {
        for(ServerPlayer player : players) {
            if(player.id == id) {
                return player;
            }
        }
        return null;
    }


    @Override
    public Engine getEngine() {
        return engine;
    }
}
