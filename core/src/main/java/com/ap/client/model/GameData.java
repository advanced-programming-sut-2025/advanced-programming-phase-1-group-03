package com.ap.client.model;

import com.ap.client.Constraints;
import com.ap.client.asset.MapAsset;
import com.ap.client.items.Inventory;
import com.ap.client.items.Item;
import com.ap.client.items.tools.Tool;
import com.ap.client.ui.widget.ItemContainer;
import com.badlogic.ashley.core.Entity;

/**
 * We use this class to store current game data
 */
public class GameData {
    private static GameData instance;

    private MapAsset startMap = MapAsset.Farm1;
    private String loggedUserUsername = null;
    private int farmIndex;
    private boolean isGreenhouseBuilt = false;
    private int gold = Constraints.PLAYER_INITIAL_GOLD;
    private Item currentTool;
    private Inventory inventory;
    private ItemContainer itemContainer;

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

    public Item getCurrentTool() {
        return currentTool;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setItemContainer(ItemContainer itemContainer) {
        this.itemContainer = itemContainer;
    }

    public ItemContainer getItemContainer() {
        return itemContainer;
    }
}
