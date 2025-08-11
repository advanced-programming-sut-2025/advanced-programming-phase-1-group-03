package com.ap.items;

import com.ap.asset.AtlasAsset;
import com.ap.managers.PlayerManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public class Item {
    protected String name;
    protected int maxStackSize;
    protected int sellPrice;

    // This texture region indicates icon of item, If item has graphic, Graphic component must be added
    protected final AtlasAsset atlasAsset;
    protected final String atlasKey;

    public Item(String name, int maxStackSize, AtlasAsset atlasAsset, String atlasKey, int sellPrice) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.sellPrice = sellPrice;
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
    }
    public Item(String name, int maxStackSize, AtlasAsset atlasAsset, String atlasKey) {
        this(name, maxStackSize, atlasAsset, atlasKey, 0);
    }
    public Item(String name, int maxStackSize) {
        this(name, maxStackSize, null, null);
    }
    public Item(String name) {
        this(name, 0);
    }

    public String getName() {
        return name;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public AtlasAsset getAtlasAsset() {
        return atlasAsset;
    }

    public String getAtlasKey() {
        return atlasKey;
    }


    /**
     * Items can override this method
     * When we want to apply item this method will be called
     */
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
    }

    /**
     * Items can override this method
     * When item picked up from ground this method will be called
     */
    public void onItemPickedUp() {
    }

    /**
     * This method create new entity for item, we use it when we want to show the item on the map
     * @return New entity
     */
    public Entity createEntity() {
        return new Entity();
    }

    /**
     * This method returns that can we stack these two items?
     * By default, it checks only names equivalency
     * @param other Other item
     * @return If true these items can place together
     */
    public boolean canStackWith(Item other) {
        return other.getName().equals(this.getName());
    }

    /**
     * This method called when player is close to the item
     */
    public void interact(Body body, Engine engine, PlayerManager playerManager) {

    }

    public static class WorldObject {
        Object getUserData;
        Vector2 position;
        public WorldObject(Object getUserData, Vector2 position) {
            this.getUserData = getUserData;
            this.position = position;
        }
        public Object getUserData() {
            return getUserData;
        }
        public Vector2 getPosition() {
            return position;
        }
    }
}
