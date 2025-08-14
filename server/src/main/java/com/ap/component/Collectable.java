package com.ap.component;

import com.ap.items.ItemStack;
import com.ap.model.ServerPlayer;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Collectable implements Component {
    public static final ComponentMapper<Collectable> mapper = ComponentMapper.getFor(Collectable.class);
    public static final float collectingRange = 2;
    public static final float receivedRange = 0.2f;
    public static final float maxSpeed = 2f;

    private ItemStack item;
    private boolean isActive = false;
    private Entity playerEntity = null;
    private ServerPlayer player = null;
    private Vector2 speed = new Vector2(0, 0);
    private final float acceleration = 0.3f;
    public Collectable(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }
}

