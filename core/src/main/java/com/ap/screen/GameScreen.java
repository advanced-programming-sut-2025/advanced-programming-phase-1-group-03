package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.tools.Tool;
import com.ap.managers.ClockManager;
import com.ap.managers.MapManager;
import com.ap.screen.maps.Farm;
import com.ap.screen.maps.IMap;
import com.ap.system.*;
import com.ap.system.universal.TimeSystem;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.view.GameView;
import com.ap.ui.widget.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.function.Consumer;

public class GameScreen extends AbstractScreen {
    private AssetService assetService;
    private AudioService audioService;
    private Camera camera;

    // UI Components
    private Clock clock;
    private ItemContainer itemContainer;
    private EnergyBar energyBar;
    private InventoryMenu inventoryMenu;

    private ClockManager clockManager;
    private MapManager mapManager;

    private Inventory inventory;

    private Engine universalEngine;

    private TimeSystem timeSystem;
    private WeatherSystem weatherSystem;

    public GameScreen(GdxGame game) {
        super(game);

        universalEngine = new Engine();

        camera = game.getCamera();
        assetService = game.getAssetService();
        audioService = game.getAudioService();

        // Setup inventory
        inventory = new Inventory();
        Tool.addBasicTools(inventory, assetService);

        clock = new Clock(assetService, skin);
        itemContainer = new ItemContainer(assetService, skin, stage, inventory, audioService);
        energyBar = new EnergyBar(assetService, skin);
        inventoryMenu = new InventoryMenu(assetService, skin, stage, inventory, audioService);

        clockManager = new ClockManager(clock);
        timeSystem = new TimeSystem();
        weatherSystem = new WeatherSystem(clock, assetService, stage, audioService, timeSystem);
        mapManager = new MapManager(game, this);
    }


    @Override
    public void show() {
        universalEngine.addSystem(timeSystem);
        universalEngine.addSystem(weatherSystem);
        universalEngine.addSystem(new EnergySystem(energyBar));

        // Play background music
        audioService.playMusic(MusicAsset.Spring);

        mapManager.setMap(MapAsset.Farm1);

        timeSystem.setup();
        weatherSystem.setup();

        // Time consumers
        Consumer<TimeSystem.Time> clockConsumer = clockManager::receive;
        timeSystem.setTimeConsumer(clockConsumer);

        stage.addActor(new GameView(stage, skin, new GameViewModel(game), audioService));
        stage.addActor(clock);
        stage.addActor(itemContainer);
        stage.addActor(energyBar);

        // Play background music
        audioService.playMusic(MusicAsset.Spring);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(1 / 30f, delta);
        universalEngine.update(delta);

        super.render(delta);

        if(mapManager != null) {
            mapManager.update(delta);
        }
    }

    public TimeSystem getTimeSystem() {
        return timeSystem;
    }

    public WeatherSystem getWeatherSystem() {
        return weatherSystem;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Clock getClock() {
        return clock;
    }

    public ItemContainer getItemContainer() {
        return itemContainer;
    }

    public EnergyBar getEnergyBar() {
        return energyBar;
    }

    public InventoryMenu getInventoryMenu() {
        return inventoryMenu;
    }

    public AssetService getAssetService() {
        return assetService;
    }

    public AudioService getAudioService() {
        return audioService;
    }

    public Camera getCamera() {
        return camera;
    }

    public MapManager getMapManger() {
        return mapManager;
    }
}
