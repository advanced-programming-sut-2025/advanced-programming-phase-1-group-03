package com.ap.client.model;

import com.ap.client.Constraints;
import com.ap.client.asset.MapAsset;
import com.badlogic.ashley.core.Entity;

/**
 * We use this class to store current game data
 */
public class GameData {
    private static GameData instance;

    private MapAsset startMap;
    private String loggedUserUsername = null;
    private int farmIndex;
    private boolean isGreenhouseBuilt = false;
    private int gold = Constraints.PLAYER_INITIAL_GOLD;
    private Entity carriedEntity = null;

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public String getLoggedUserUsername() {
        return loggedUserUsername;
    }

    public void setLoggedUserUsername(String loggedUserUsername) {
        this.loggedUserUsername = loggedUserUsername;
    }

    public int getFarmIndex() {
        return farmIndex;
    }

    public void setFarmIndex(int farmIndex) {
        this.farmIndex = farmIndex;
    }

    public MapAsset getStartMap() {
        return startMap;
    }

    public void setStartMap(MapAsset startMap) {
        this.startMap = startMap;
    }

    public boolean isGreenhouseBuilt() {
        return isGreenhouseBuilt;
    }

    public void setGreenhouseBuilt(boolean greenhouseBuilt) {
        isGreenhouseBuilt = greenhouseBuilt;
    }

    public int getPlayerGold() {
        return gold;
    }

    public void setPlayerGold(int i) {
        gold = i;
    }

    public Entity getCarriedEntity() {
        return carriedEntity;
    }

    public void setCarriedEntity(Entity carriedEntity) {
        this.carriedEntity = carriedEntity;
    }
}
