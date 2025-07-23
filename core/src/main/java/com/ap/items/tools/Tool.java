package com.ap.items.tools;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.model.Abilities;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tool extends Item {
    protected final Abilities relatedAbility;

    public Tool(String name, TextureRegion icon, Abilities relatedAbility) {
        super(name, 1, icon);
        this.relatedAbility = relatedAbility;
    }

    /**
     * This method will return energy consumption for each usage based on level of tool
     * @return Energy amount
     */
    abstract int getEnergyConsumption();

    /**
     * This method add primary tools to the inventory
     */
    public static void addBasicTools(Inventory inventory, AssetService assetService) {
        TextureAtlas atlas = assetService.get(AtlasAsset.Tools);
        inventory.addItem(new Axe(atlas.findRegion("axe/normal")), 1);
        inventory.addItem(new Pickaxe(atlas.findRegion("pickaxe/normal")), 1);
        inventory.addItem(new Hoe(atlas.findRegion("hoe/normal")), 1);

    }
}
