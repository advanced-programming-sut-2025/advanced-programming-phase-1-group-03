package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.asset.AssetService;
import com.ap.client.asset.MapAsset;
import com.ap.client.asset.MusicAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.items.EntityFactory;
import com.ap.client.items.Inventory;
import com.ap.client.items.ItemFactory;
import com.ap.client.items.tools.Tool;
import com.ap.client.managers.*;
import com.ap.client.model.BarnsType;
import com.ap.client.model.GameData;
import com.ap.client.model.Season;
import com.ap.client.system.*;
import com.ap.client.managers.EnergyManager;
import com.ap.client.system.universal.ITimeListener;
import com.ap.client.system.universal.TimeSystem;
import com.ap.client.ui.model.GameViewModel;
import com.ap.client.ui.view.GameView;
import com.ap.client.ui.widget.*;
import com.ap.client.ui.widget.cheatCode.CheatCodeBox;
import com.ap.client.ui.widget.cheatCode.CheatCodeController;
import com.ap.client.ui.widget.tabContents.TabManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen extends AbstractScreen {
    private AssetService assetService;
    private AudioService audioService;
    private Camera camera;

    // UI Components
    private Clock clock;
    private ItemContainer itemContainer;
    private EnergyBar energyBar;
    private CraftingMenu craftingMenu;
    private CookingMenu cookingMenu;
    private LightningStorm lightningStorm;
    private TabManager tabManager;
    private Journal journal;
    private CheatCodeBox cheatCodeBox;
    private AnimalStatMenu animalStatMenu;

    private ClockManager clockManager;
    private MapManager mapManager;
    private CheatCodeController cheatCodeController;
    private AnimalManager animalManager;

    private Inventory inventory;
    private AbilityManager abilityManager;

    private Engine universalEngine;

    private TimeSystem timeSystem;
    private WeatherSystem weatherSystem;
    private WeatherEffects weatherEffects;

    private MapAsset currentMap;
    private TiledMap currentTiledMap;

    private EnergyManager energyManager;

    private Map<MapAsset, Engine> engineCache = new HashMap<>();
    private Map<MapAsset, World> worldCache = new HashMap<>();

    public GameScreen(GdxGame game) {
        super(game);
        universalEngine = new Engine();
//        skin.getFont("roboto16").getData().markupEnabled = true;
//        skin.getFont("roboto20").getData().markupEnabled = true;
//        skin.getFont("roboto24").getData().markupEnabled = true;
//        skin.getFont("roboto28").getData().markupEnabled = true;
//
//        skin.getFont("robotoBold16").getData().markupEnabled = true;
//        skin.getFont("robotoBold20").getData().markupEnabled = true;
//        skin.getFont("robotoBold24").getData().markupEnabled = true;
//        skin.getFont("robotoBold28").getData().markupEnabled = true;
//
//        for (int i = 12; i <= 24; i++) {
//            skin.getFont("myFont" + i).getData().markupEnabled = true;
//            skin.getFont("Mill" + i).getData().markupEnabled = true;
//        }
//
//        skin.getFont("font16").getData().markupEnabled = true;
//        skin.getFont("font20").getData().markupEnabled = true;
//        skin.getFont("font24").getData().markupEnabled = true;
//        skin.getFont("font24white").getData().markupEnabled = true;
//        skin.getFont("font28").getData().markupEnabled = true;
//        skin.getFont("font36").getData().markupEnabled = true;
//        skin.getFont("font48").getData().markupEnabled = true;
        camera = game.getCamera();
        assetService = game.getAssetService();
        audioService = game.getAudioService();

        GameUIManager.instance.setup(stage, skin, audioService, this);
        ItemFactory.instance.setAssetService(assetService);
        EntityFactory.instance.setup(assetService, audioService);

        // Setup inventory
        TooltipHelper.setTooltip(skin);
        TooltipHelper tooltipHelper = TooltipHelper.getTooltip();
        inventory = new Inventory();
        GameData.getInstance().setInventory(inventory);
        Tool.addBasicTools(inventory, assetService);
        abilityManager = new AbilityManager();

        clock = new Clock(assetService, skin);
        itemContainer = new ItemContainer(assetService, skin, stage, inventory, audioService);
        GameData.getInstance().setItemContainer(itemContainer);
        energyBar = new EnergyBar(assetService, skin);

        journal = new Journal(assetService, skin, stage);
        craftingMenu = new CraftingMenu(assetService, skin, stage, inventory, audioService);
        cheatCodeController = new CheatCodeController(this);
        cheatCodeBox = new CheatCodeBox(stage, skin, cheatCodeController);
        lightningStorm = new LightningStorm(assetService, skin, stage, audioService, 400, 400);
        cookingMenu =  new CookingMenu(assetService, skin, stage, inventory, audioService);
        tabManager = new TabManager(this);
        clockManager = new ClockManager(clock);
        timeSystem = new TimeSystem();
        weatherSystem = new WeatherSystem(clock, timeSystem);
        energyManager = new EnergyManager(weatherSystem, abilityManager);
        animalStatMenu = new AnimalStatMenu(null, stage, skin, assetService, audioService);
        animalManager = new AnimalManager(this);
        AnimalManager.instance = animalManager;

        mapManager = new MapManager(game, this);
        mapManager.loadAllMaps();
    }


    @Override
    public void show() {
        universalEngine.addSystem(timeSystem);
        universalEngine.addSystem(weatherSystem);
        universalEngine.addSystem(new EnergySystem(energyBar, energyManager));
        // Play background music
        audioService.playMusic(MusicAsset.Spring);

        mapManager.setMap(GameData.getInstance().getStartMap());

        // Time consumers
        timeSystem.addTimeListener(new TimeListener());

        stage.addActor(new GameView(stage, skin, new GameViewModel(game), audioService));
        stage.addActor(clock);
        stage.addActor(itemContainer);
        stage.addActor(energyBar);
        stage.addActor(journal);
        stage.addActor(TooltipHelper.getTooltip());

//        lightningStorm.toggle(0, 0);

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

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public Clock getClock() {
        return clock;
    }

    public ItemContainer getItemContainer() {
        return itemContainer;
    }

    public CraftingMenu getCraftingMenu() {
        return craftingMenu;
    }

    public EnergyBar getEnergyBar() {
        return energyBar;
    }

    public LightningStorm getLightningStorm() {
        return lightningStorm;
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

    public TabManager getTabManager() {
        return tabManager;
    }

    public CookingMenu getCookingMenu() {
        return cookingMenu;
    }

    public CheatCodeBox getCheatCodeBox() {
        return cheatCodeBox;
    }

    public MapAsset getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(MapAsset currentMap) {
        this.currentMap = currentMap;
    }

    public TiledMap getCurrentTiledMap() {
        return currentTiledMap;
    }

    public void setCurrentTiledMap(TiledMap currentTiledMap) {
        this.currentTiledMap = currentTiledMap;
    }

    public Map<MapAsset, Engine> getEngineCache() {
        return engineCache;
    }

    public Map<MapAsset, World> getWorldCache() {
        return worldCache;
    }

    public Engine getFarmEngine() {
        var engine = engineCache.get(MapAsset.Farm1);
        if(engine == null) {
            return engineCache.get(MapAsset.Farm2);
        }
        return engine;
    }
    public World getFarmWorld() {
        var world = worldCache.get(MapAsset.Farm1);
        if(world == null) {
            return worldCache.get(MapAsset.Farm2);
        }
        return world;
    }
    public World getMapWorld(MapAsset mapAsset) {
        return worldCache.get(mapAsset);
    }
    public Engine getBarnEngine(BarnsType barnsType) {
        return engineCache.get(MapAsset.valueOf(barnsType.name()));
    }

    public EnergyManager getEnergyManager() {
        return energyManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public AnimalStatMenu getAnimalStatMenu() {
        return animalStatMenu;
    }

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
        }

        @Override
        public void onDayChanged(int day) {
            weatherSystem.setWeatherRandomly();
        }

        @Override
        public void onFrameChanged(TimeSystem.Time newTime) {
            clockManager.receive(newTime);
        }
    }
}
