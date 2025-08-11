package com.ap.client.items.tools;

import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.items.Inventory;
import com.ap.client.items.Item;
import com.ap.client.items.ItemFactory;
import com.ap.client.model.AbilityType;
import com.ap.client.model.CropsType;
import com.ap.client.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Tool extends Item {
    protected final AbilityType relatedAbility;

    public Tool(String name, TextureRegion icon, AbilityType relatedAbility) {
        super(name, 1, icon);
        this.relatedAbility = relatedAbility;
    }

    /**
     * This method will return energy consumption for each usage based on level of tool
     * @return Energy amount
     */
    public abstract int getEnergyConsumption();

    /**
     * This method add primary tools to the inventory
     */
    public static void addBasicTools(Inventory inventory, AssetService assetService) {
        TextureAtlas atlas = assetService.get(AtlasAsset.Tools);
        inventory.addItem(new Axe(atlas.findRegion("axe/normal")), 1);
        inventory.addItem(new Pickaxe(atlas.findRegion("pickaxe/normal")), 1);
        inventory.addItem(new Hoe(atlas.findRegion("hoe/normal")), 1);
        inventory.addItem(new FishingPole(atlas.findRegion("fishing_pole/training")), 1);
        inventory.addItem(new MilkPail(atlas.findRegion("milk_pail/normal")), 1);
        inventory.addItem(new Scythe(atlas.findRegion("scythe/normal")), 1);
        inventory.addItem(new Shear(atlas.findRegion("shear/normal")), 1);
        inventory.addItem(new WateringCan(atlas.findRegion("watering_can/normal")), 1);
        inventory.addItem(ItemFactory.instance.CreateSeed(CropsType.Cauliflower), 9);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        game.getEnergyManager().reduceByUsingTool(this);
    }

    public AbilityType getRelatedAbility() {
        return relatedAbility;
    }

    public enum BasicToolLevels {
        Normal,
        Copper,
        Iron,
        Gold,
        Iridium;

        public boolean isStrictlyGreater(BasicToolLevels o) {
            return this.ordinal() < o.ordinal();
        }
    }
}
