package com.ap.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Item {
    protected String name;
    protected int maxStackSize;
    protected int sellPrice;

    // This texture region indicates icon of item, If item has graphic, Graphic component must be added
    protected final TextureRegion icon;

    public Item(String name, int maxStackSize, TextureRegion icon, int sellPrice) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.sellPrice = sellPrice;
        this.icon = icon;
    }
    public Item(String name, int maxStackSize, TextureRegion icon) {
        this(name, maxStackSize, icon, 0);
    }
    public Item(String name, int maxStackSize) {
        this(name, maxStackSize, null);
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

    public TextureRegion getIcon() {
        return icon;
    }

    /**
     * Items can override this method
     * When we want to apply item this method will be called
     */
    public void applyItem() {
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

}
