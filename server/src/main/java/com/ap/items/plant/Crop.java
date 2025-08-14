package com.ap.items.plant;

import com.ap.asset.AtlasAsset;
import com.ap.component.Player;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.managers.PlayerManager;
import com.ap.model.CropsType;
import com.ap.model.EmoteType;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

public class Crop extends Plant {
    private CropsType type;
    private boolean isGiant = false;
    private boolean isRegrowing = false;

    // This field store how many times the crop was hit by axe
    private int axeHit = 0;

    public Crop(AtlasAsset atlasAsset, String atlasKey, CropsType type) {
        super(type.getName(), 0, atlasAsset, atlasKey, 0);
        this.type = type;
    }

    @Override
    public List<Item> produceItems() {
        return List.of(ItemFactory.instance.CreateProductOfCrop(type));
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {

    }

    public CropsType getType() {
        return type;
    }

    public void setupRegrowth() {
        isRegrowing = true;
    }

    public boolean isRegrowing() {
        return isRegrowing;
    }

    public void setRegrowing(boolean regrowing) {
        isRegrowing = regrowing;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public int getAxeHit() {
        return axeHit;
    }

    public void setAxeHit(int axeHit) {
        this.axeHit = axeHit;
    }
}
