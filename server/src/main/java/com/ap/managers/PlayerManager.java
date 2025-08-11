package com.ap.managers;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.ItemFactory;
import com.ap.items.tools.Tool;
import com.ap.maps.Farm;
import com.ap.model.GameManager;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ShowMessageNotifier;
import com.ap.requests.MovePlayerRequest;
import com.ap.system.universal.TimeSystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;

public class PlayerManager {
    private final GameManager gameManager;
    private MapManager mapManager;
    private final ServerPlayer player;
    private MapAsset farmMap;
    private boolean isGreenhouseBuilt = false;
    private Inventory inventory;
    private final AudioService audioService;
    private final MessageSender messageSender;

    public PlayerManager(GameManager gameManager, ServerPlayer player) {
        this.gameManager = gameManager;
        this.player = player;
        this.messageSender = new MessageSender(player);
        audioService = new AudioService(player);
        inventory = new Inventory(player);
    }
    public void setupMap(MapAsset map) {
        this.farmMap = map;
        mapManager = new MapManager(gameManager, this, map, player);
        Tool.addBasicTools(inventory);
    }

    public void update(float delta) {
        if(mapManager != null) {
            mapManager.update(delta);
        }
    }

    public void move(MovePlayerRequest movePlayerRequest) {
        mapManager.currentMap.movePlayer(movePlayerRequest);
    }

    public boolean isGreenhouseBuilt() {
        return isGreenhouseBuilt;
    }

    public MapAsset getFarmMap() {
        return farmMap;
    }

    public AssetService getAssetService() {
        return gameManager.getAssetService();
    }

    public AudioService getAudioService() {
        return audioService;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public void applyItem(int index, int x, int y) {
        mapManager.currentMap.applyItem(index, x, y);

    }

    public void applyReaction() {

    }

    public void buildGreenhouse() {
        if(mapManager.currentMap instanceof Farm farm) {
            farm.buildGreenhouse();
        }
    }

    public TimeSystem getTimeSystem() {
        return gameManager.getTimeSystem();
    }

    public void setBuildGreenhouse(boolean b) {
        isGreenhouseBuilt = true;
    }
}
