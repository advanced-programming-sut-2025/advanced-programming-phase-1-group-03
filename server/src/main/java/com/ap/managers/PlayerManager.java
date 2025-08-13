package com.ap.managers;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.ItemFactory;
import com.ap.items.Refrigerator;
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

import java.util.ArrayList;

public class PlayerManager {
    private final GameManager gameManager;
    private final ServerPlayer player;
    private MapAsset farmMap;
    private Engine farmEngine;
    private boolean isGreenhouseBuilt = false;
    private Inventory inventory;
    private Refrigerator refrigerator;
    private final MessageSender messageSender;
    private final AbilityManager abilityManager;
    private final AudioService audioService;

    private final ArrayList<ServerPlayer> activeFromTradeRequests = new ArrayList<>();
    private final ArrayList<ServerPlayer> activeToTradeRequests = new ArrayList<>();

    public PlayerManager(GameManager gameManager, ServerPlayer player) {
        this.gameManager = gameManager;
        this.player = player;
        this.messageSender = new MessageSender(player);
        inventory = new Inventory(player);
        refrigerator = new Refrigerator(player);
        abilityManager = new AbilityManager();

        audioService = new AudioService(player);
        Tool.addBasicTools(inventory);
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public MapAsset getCurrentMapAsset() {
        return gameManager.getMapManager().currentMapAssets.get(player);
    }
    public void move(MovePlayerRequest movePlayerRequest, ServerPlayer senderPlayer) {
        gameManager.getMapManager().currentMaps.get(senderPlayer).movePlayer(movePlayerRequest, senderPlayer);
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

    public Inventory getInventory() {
        return inventory;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public void applyItem(int index, int x, int y, ServerPlayer senderPlayer) {
        gameManager.getMapManager().currentMaps.get(senderPlayer).applyItem(index, x, y, senderPlayer.id);
    }

    public void applyReaction(int emojiNum) {
        //TODO implement reaction here
    }

    public void applyReaction(String message) {
        //TODO implement reaction message here
    }

    public void buildGreenhouse(ServerPlayer serverPlayer) {
        if(gameManager.getMapManager().currentMaps.get(serverPlayer) instanceof Farm farm) {
            farm.buildGreenhouse(serverPlayer);
        }
    }

    public TimeSystem getTimeSystem() {
        return gameManager.getTimeSystem();
    }

    public void setBuildGreenhouse(boolean b) {
        isGreenhouseBuilt = true;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void setFarmMap(MapAsset farmMap) {
        this.farmMap = farmMap;
    }

    public AudioService getAudioService() {
        return audioService;
    }

    public void setFarmEngine(Engine farmEngine) {
        this.farmEngine = farmEngine;
    }

    public Engine getFarmEngine() {
        return farmEngine;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArrayList<ServerPlayer> getActiveFromTradeRequests() {
        return activeFromTradeRequests;
    }

    public ArrayList<ServerPlayer> getActiveToTradeRequests() {
        return activeToTradeRequests;
    }
}
