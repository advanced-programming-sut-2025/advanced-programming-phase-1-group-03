package com.ap.managers;

import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.model.*;
import com.ap.notifiers.ChangeSeasonNotifier;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.NotifySystem;
import com.ap.system.universal.TimeSystem;
import com.ap.system.universal.WeatherSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;

public class GameManager {
    private final Room room;
    private final Engine universalEngine;

    private AssetService assetService;

    private TimeSystem timeSystem;
    private WeatherSystem weatherSystem;
    private NotifySystem notifySystem;

    private MapAsset currentMap;
    private TiledMap currentTiledMap;

    private final MapManager mapManager;

    private final ArrayList<TradeRoom> tradeRooms = new ArrayList<>();

    private final ArrayList<TradeHistory> tradeHistory = new ArrayList<>();

    public GameManager(Room room, AssetService assetService) {
        this.room = room;

        this.assetService = assetService;

        universalEngine = new Engine();

        mapManager = new MapManager(this);

        addSystems();
    }

    private void addSystems() {
        timeSystem = new TimeSystem();
        weatherSystem = new WeatherSystem(timeSystem, room);

        universalEngine.addSystem(timeSystem);
        timeSystem.addTimeListener(new TimeListener());


        notifySystem = new NotifySystem(room, universalEngine);
        universalEngine.addSystem(notifySystem);
    }

    public void playerJoined(ServerPlayer player, MapAsset map) {
        player.playerManager = new PlayerManager(this, player);
        player.playerManager.setFarmMap(map);
        // We notify the client the amount of gold we have at the start
        player.advanceGold(0);
        mapManager.playerArrived(player, map, player.playerManager);
    }

    public void update(float delta) {
        universalEngine.update(delta);

        // Update active engine of players
        mapManager.update(delta);
    }

    public TimeSystem getTimeSystem() {
        return timeSystem;
    }

    public WeatherSystem getWeatherSystem() {
        return weatherSystem;
    }

    public NotifySystem getNotifierSystem() {
        return notifySystem;
    }

    public AssetService getAssetService() {
        return assetService;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ArrayList<TradeRoom> getTradeRooms() {
        return tradeRooms;
    }

    public ArrayList<TradeHistory> getTradeHistory() {
        return tradeHistory;
    }

    private class TimeListener implements ITimeListener {
        @Override
        public void onDayChanged(int newDay) {
            weatherSystem.setWeatherRandomly();
            mapManager.goToHome();
        }

        @Override
        public void onSeasonChanged(Season newSeason) {
            room.broadcast(new ChangeSeasonNotifier(newSeason));
        }
    }

//
//
//
//        // Setup inventory
//        TooltipHelper.setTooltip(skin);
//        TooltipHelper tooltipHelper = TooltipHelper.getTooltip();
//        inventory = new Inventory();
//        Tool.addBasicTools(inventory, assetService);
//        abilityManager = new AbilityManager();
//
//        clock = new Clock(assetService, skin);
//        itemContainer = new ItemContainer(assetService, skin, stage, inventory, audioService);
//        energyBar = new EnergyBar(assetService, skin);
//
//        journal = new Journal(assetService, skin, stage);
//        craftingMenu = new CraftingMenu(assetService, skin, stage, inventory, audioService);
//        cheatCodeController = new CheatCodeController(this);
//        cheatCodeBox = new CheatCodeBox(stage, skin, cheatCodeController);
//        lightningStorm = new LightningStorm(assetService, skin, stage, audioService, 400, 400);
//        cookingMenu =  new CookingMenu(assetService, skin, stage, inventory, audioService);
//        tabManager = new TabManager(this);
//        clockManager = new ClockManager(clock);
//        timeSystem = new TimeSystem();
//        weatherSystem = new WeatherSystem(clock, timeSystem);
//
//        energyManager = new EnergyManager(weatherSystem, abilityManager);
//
//        mapManager = new MapManager(game, this);
//        mapManager.loadAllMaps();
//    }
//
//
//    @Override
//    public void show() {
//        client.getListener(GameListener.class).setGameScreen(this);
//
//        universalEngine.addSystem(timeSystem);
//        universalEngine.addSystem(weatherSystem);
//        universalEngine.addSystem(new EnergySystem(energyBar, energyManager));
//        // Play background music
//        audioService.playMusic(MusicAsset.Spring);
//
//        mapManager.setMap(GameData.getInstance().getStartMap());
//
//        // Time consumers
//        timeSystem.addTimeListener(new GameScreen.TimeListener());
//
//        stage.addActor(new GameView(stage, skin, new GameViewModel(game), audioService));
//        stage.addActor(clock);
//        stage.addActor(itemContainer);
//        stage.addActor(energyBar);
//        stage.addActor(journal);
//        stage.addActor(TooltipHelper.getTooltip());
//
////        lightningStorm.toggle(0, 0);
//
//        // Play background music
//        audioService.playMusic(MusicAsset.Spring);
//    }
//
//    @Override
//    public void render(float delta) {
//        delta = Math.min(1 / 30f, delta);
//        universalEngine.update(delta);
//
//        super.render(delta);
//
//        if(mapManager != null) {
//            mapManager.update(delta);
//        }
//    }
//
//    public TimeSystem getTimeSystem() {
//        return timeSystem;
//    }
//
//    public WeatherSystem getWeatherSystem() {
//        return weatherSystem;
//    }
//
//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public AbilityManager getAbilityManager() {
//        return abilityManager;
//    }
//
//    public Clock getClock() {
//        return clock;
//    }
//
//    public ItemContainer getItemContainer() {
//        return itemContainer;
//    }
//
//    public CraftingMenu getCraftingMenu() {
//        return craftingMenu;
//    }
//
//    public EnergyBar getEnergyBar() {
//        return energyBar;
//    }
//
//    public LightningStorm getLightningStorm() {
//        return lightningStorm;
//    }
//
//    public AssetService getAssetService() {
//        return assetService;
//    }
//
//    public AudioService getAudioService() {
//        return audioService;
//    }
//
//    public Camera getCamera() {
//        return camera;
//    }
//
//    public MapManager getMapManger() {
//        return mapManager;
//    }
//
//    public TabManager getTabManager() {
//        return tabManager;
//    }
//
//    public CookingMenu getCookingMenu() {
//        return cookingMenu;
//    }
//
//    public CheatCodeBox getCheatCodeBox() {
//        return cheatCodeBox;
//    }
//
//    public MapAsset getCurrentMap() {
//        return currentMap;
//    }
//
//    public void setCurrentMap(MapAsset currentMap) {
//        this.currentMap = currentMap;
//    }
//
//    public TiledMap getCurrentTiledMap() {
//        return currentTiledMap;
//    }
//
//    public void setCurrentTiledMap(TiledMap currentTiledMap) {
//        this.currentTiledMap = currentTiledMap;
//    }
//
//    public Map<MapAsset, Engine> getEngineCache() {
//        return engineCache;
//    }
//    public Engine getFarmEngine() {
//        var engine = engineCache.get(MapAsset.Farm1);
//        if(engine == null) {
//            return engineCache.get(MapAsset.Farm2);
//        }
//        return engine;
//    }
//
//    public EnergyManager getEnergyManager() {
//        return energyManager;
//    }
//
//    public Skin getSkin() {
//        return skin;
//    }
//
//
//    class TimeListener implements ITimeListener {
//
//        @Override
//        public void onSeasonChanged(Season season) {
//        }
//
//        @Override
//        public void onDayChanged(int day) {
//            weatherSystem.setWeatherRandomly();
//        }
//
//        @Override
//        public void onFrameChanged(TimeSystem.Time newTime) {
//            clockManager.receive(newTime);
//        }
//    }
}
