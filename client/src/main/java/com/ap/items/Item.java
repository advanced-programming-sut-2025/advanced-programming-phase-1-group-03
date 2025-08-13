package com.ap.items;

import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public class Item {
    protected String name;

    // This texture region indicates icon of item, If item has graphic, Graphic component must be added
    protected final TextureRegion icon;

    private int index;

    public Item(String name, TextureRegion icon, int index) {
        this.icon = icon;
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }

    public TextureRegion getIcon() {
        return icon;
    }

    /**
     * Items can override this method
     * When we want to apply item this method will be called
     */
    public void applyItem(GameScreen game, int x, int y) {
        game.getGameClient().getSender().applyItem(index, x, y);
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
