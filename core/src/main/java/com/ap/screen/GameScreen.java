package com.ap.screen;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.component.Player;
import com.ap.items.EntityFactory;
import com.ap.items.Inventory;
import com.ap.items.ItemFactory;
import com.ap.items.tools.Tool;
import com.ap.managers.*;
import com.ap.model.GameData;
import com.ap.model.Season;
import com.ap.system.*;
import com.ap.system.universal.EnergyManager;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.TimeSystem;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.view.GameView;
import com.ap.ui.widget.*;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.ui.widget.cheatCode.CheatCodeController;
import com.ap.ui.widget.tabContents.TabManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
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

    private ClockManager clockManager;
    private MapManager mapManager;
    private CheatCodeController cheatCodeController;

    private Inventory inventory;
    private AbilityManager abilityManager;

    private Engine universalEngine;

    private TimeSystem timeSystem;
    private WeatherSystem weatherSystem;
    private WeatherEffects weatherEffects;

    private MapAsset currentMap;
    private TiledMap currentTiledMap;

    private EnergyManager energyManager;

    public GameScreen(GdxGame game) {
        super(game);
        universalEngine = new Engine();

        camera = game.getCamera();
        assetService = game.getAssetService();
        audioService = game.getAudioService();

        GameUIManager.instance.setup(stage, skin, audioService, this);
        ItemFactory.instance.setAssetService(assetService);
        EntityFactory.instance.setup(assetService, audioService);

        // Setup inventory
        TooltipHelper tooltipHelper = TooltipHelper.getTooltip(skin);
        inventory = new Inventory();
        Tool.addBasicTools(inventory, assetService);
        abilityManager = new AbilityManager();
        AbilityManager.setINSTANCE(abilityManager);

        clock = new Clock(assetService, skin);
        itemContainer = new ItemContainer(assetService, skin, stage, inventory, audioService);
        energyBar = new EnergyBar(assetService, skin);
        tabManager = new TabManager(stage, assetService, skin, audioService, inventory);
        energyManager = EnergyManager.getInstance();

        journal = new Journal(assetService, skin, stage);
        craftingMenu = new CraftingMenu(assetService, skin, stage, inventory, audioService);
        cheatCodeController = new CheatCodeController(this);
        cheatCodeBox = new CheatCodeBox(stage, skin, cheatCodeController);
        lightningStorm = new LightningStorm(assetService, skin, stage, audioService, 400, 400);
        cookingMenu =  new CookingMenu(assetService, skin, stage, inventory, audioService);
        stage.addActor(tooltipHelper);
        clockManager = new ClockManager(clock);
        timeSystem = new TimeSystem();
        weatherSystem = new WeatherSystem(clock, timeSystem);
        mapManager = new MapManager(game, this);
        mapManager.loadAllMaps();
    }


    @Override
    public void show() {
        universalEngine.addSystem(timeSystem);
        universalEngine.addSystem(weatherSystem);
        universalEngine.addSystem(new EnergySystem(energyBar));
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
        lightningStorm.toggle(0, 0);

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
