package com.ap.screen;

import box2dLight.RayHandler;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.audio.AudioService;
import com.ap.audio.VoiceChatClient;
import com.ap.input.KeyboardController;
import com.ap.items.Inventory;
import com.ap.items.ItemStack;
import com.ap.managers.*;
import com.ap.model.GameData;
import com.ap.network.GameClient;
import com.ap.network.listeners.GameListener;
import com.ap.notifiers.ChangeSeasonNotifier;
import com.ap.notifiers.CreateMapNotifier;
import com.ap.system.*;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.view.GameView;
import com.ap.ui.widget.*;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.ui.widget.cheatCode.CheatCodeController;
import com.ap.ui.widget.tabContents.TabManager;
import com.ap.system.universal.TimeSystem;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends AbstractScreen {
    private AssetService assetService;
    private AudioService audioService;
    private Camera camera;
    private Batch batch;

    private KeyboardController keyboardController;

    // UI Components
    private Clock clock;
    private ItemContainer itemContainer;
    private EnergyBar energyBar;
    private CraftingMenu craftingMenu;
    private CookingMenu cookingMenu;
    private LightningStorm lightningStorm;
    private TabManager tabManager;
    private TradeStarterMenu tradeStarterMenu;
    private TradeMenu tradeMenu;
    private Journal journal;
    private CheatCodeBox cheatCodeBox;
    private LeaderBoard leaderBoard;
    private RefrigeratorMenu refrigeratorMenu;
    private AnimalStatMenu animalStatMenu;
    private ProcessingMachineWindow processingMachineWindow;

    private EmojiPanel emojiPanel;

    private ClockManager clockManager;
    private CheatCodeController cheatCodeController;

    private Inventory inventory;
    private Inventory refrigerator;
    private AbilityManager abilityManager;

    private Engine universalEngine;

    private TimeSystem timeSystem;
    private WeatherSystem weatherSystem;

    private MapAsset currentMap;
    private TiledMap currentTiledMap;

    private Map<MapAsset, Engine> engineCache = new HashMap<>();

    private GameClient client;


    private NetworkGameManager networkGameManager;

    private RayHandler rayHandler;

    private VoiceChatClient voiceChat;

    public boolean sendVoiceMessage = false;

    private StoreManager storeManager;

    public GameScreen(GdxGame game) {
        super(game);
        universalEngine = new Engine();

        World world = new World(new Vector2(0, 0), true);
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);

        batch = game.getBatch();

        client = game.getClient();

        camera = game.getCamera();
        assetService = game.getAssetService();
        audioService = game.getAudioService();

        GameUIManager.instance.setup(stage, skin, audioService, this);

        // Setup inventory
        TooltipHelper.setTooltip(skin);
        TooltipHelper tooltipHelper = TooltipHelper.getTooltip();
        inventory = new Inventory(assetService);
        refrigerator = new Inventory(assetService);
        abilityManager = new AbilityManager();

        clock = new Clock(assetService, skin);
        itemContainer = new ItemContainer(assetService, skin, stage, inventory, audioService);
        energyBar = new EnergyBar(assetService, skin);

        journal = new Journal(assetService, skin, stage);
        craftingMenu = new CraftingMenu(assetService, skin, stage, inventory, audioService);
        cheatCodeController = new CheatCodeController(this);
        cheatCodeBox = new CheatCodeBox(stage, skin, cheatCodeController);
        lightningStorm = new LightningStorm(assetService, skin, stage, audioService, 400, 400);
        cookingMenu =  new CookingMenu(assetService, skin, stage, inventory, audioService, this);
        tabManager = new TabManager(this);
        tradeStarterMenu = new TradeStarterMenu(this);
        tradeMenu = new TradeMenu(this);
        clockManager = new ClockManager(clock);
        leaderBoard = new LeaderBoard(assetService, stage, this);
        emojiPanel = EmojiPanel.getInstance(skin, stage, assetService, this);
        refrigeratorMenu = new RefrigeratorMenu(assetService, skin, stage, inventory, refrigerator, audioService, this);
        timeSystem = new TimeSystem();
        weatherSystem = new WeatherSystem(clock);
        animalStatMenu = new AnimalStatMenu(null, getStage(), getSkin(), assetService, audioService, this);
        processingMachineWindow = new ProcessingMachineWindow(skin, stage, assetService);
        client.getListener(GameListener.class).setGameScreen(this);

        voiceChat = new VoiceChatClient();
        voiceChat.addReceiver(client.getClient());

        networkGameManager = new NetworkGameManager(this, game);

        storeManager = new StoreManager(audioService, client);
    }


    @Override
    public void show() {

        timeSystem.addListener(clockManager::receive);

        universalEngine.addSystem(weatherSystem);
        universalEngine.addSystem(new EnergySystem(energyBar));

        // Play background music
        //audioService.playMusic(MusicAsset.Spring);

       // mapManager.setMap(GameData.getInstance().getStartMap());

        stage.addActor(new GameView(stage, skin, new GameViewModel(game), audioService));
        stage.addActor(clock);
        stage.addActor(itemContainer);
        stage.addActor(energyBar);
        stage.addActor(journal);
        stage.addActor(TooltipHelper.getTooltip());
        //stage.addActor(processingMachineWindow);
//        lightningStorm.toggle(0, 0);

        // Play background music
        //audioService.playMusic(MusicAsset.Spring);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(1 / 30f, delta);
        universalEngine.update(delta);

        networkGameManager.update(delta);

        super.render(delta);

        if(sendVoiceMessage) {
            voiceChat.sendVoice(client.getClient(), delta);
        }
//        if(mapManager != null) {
//           mapManager.update(delta);
//        }
    }

    public void updateEntity(int engineId, int itemId, Component[] components) {
        networkGameManager.updateEntity(engineId, itemId, components);
    }

    public WeatherSystem getWeatherSystem() {
        return weatherSystem;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Inventory getRefrigerator() {
        return refrigerator;
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


    public RefrigeratorMenu getRefrigeratorMenu() {
        return refrigeratorMenu;
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
    public Engine getFarmEngine() {
        var engine = engineCache.get(MapAsset.Farm1);
        if(engine == null) {
            return engineCache.get(MapAsset.Farm2);
        }
        return engine;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public Skin getSkin() {
        return skin;
    }

    public ClockManager getClockManager() {
        return clockManager;
    }

    public TimeSystem getTimeSystem() {
        return timeSystem;
    }

    public void setAnimation(String atlasKey, AtlasAsset atlasAsset, int entityId, int engineId , float speed, Animation.PlayMode playMode) {
        networkGameManager.setAnimation(atlasKey, atlasAsset, entityId, engineId, speed, playMode);
    }

    public void changeMap(int engineId) {
        networkGameManager.changeMap(engineId);
    }

    public void createMap(CreateMapNotifier createMapNotifier) {
        networkGameManager.createMap(createMapNotifier.mapAsset,
                createMapNotifier.engineId,
                createMapNotifier.showWeather,
                createMapNotifier.tileSelectionSystem);
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Viewport getViewport() {
        return game.getViewport();
    }

    public GameClient getGameClient() {
        return client;
    }

    public void removeEntity(int entityId, int engineId) {
        networkGameManager.removeEntity(entityId, engineId);
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void seasonChanged(ChangeSeasonNotifier changeSeasonNotifier) {
        networkGameManager.seasonChanged(changeSeasonNotifier.season);
    }

    public EmojiPanel getEmojiPanel() {
        return emojiPanel;
    }

    public VoiceChatClient getVoiceChat() {
        return voiceChat;
    }

    public TradeStarterMenu getTradeStarterMenu() {
        return tradeStarterMenu;
    }

    public TradeMenu getTradeMenu() {
        return tradeMenu;
    }

    public void updateGold(int gold) {
        clock.setGold(gold);
        GameData.getInstance().setPlayerGold(gold);
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void updateEnergy(float amount) {
        energyBar.setEnergyPercent(amount);
    }

    public AnimalStatMenu getAnimalStatMenu() {
        return animalStatMenu;
    }

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
//    }
}
