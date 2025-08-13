package com.ap.items.tools;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.managers.PlayerManager;
import com.ap.model.AbilityType;
import com.ap.model.CropsType;
import com.ap.model.MixedSeedsTypes;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Tool extends Item {
    protected final AbilityType relatedAbility;

    public Tool(String name, AtlasAsset atlasAsset, String atlasKey, AbilityType relatedAbility) {
        super(name, 1, atlasAsset, atlasKey);
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
    public static void addBasicTools(Inventory inventory) {
        inventory.addItem(new Axe(AtlasAsset.Tools, "axe/normal"), 1);
        inventory.addItem(new Pickaxe(AtlasAsset.Tools, "pickaxe/normal"), 1);
        inventory.addItem(new Hoe(AtlasAsset.Tools, "hoe/normal"), 1);
        inventory.addItem(new FishingPole(AtlasAsset.Tools, "fishing_pole/training"), 1);
        inventory.addItem(new MilkPail(AtlasAsset.Tools, "milk_pail/normal"), 1);
        inventory.addItem(new Scythe(AtlasAsset.Tools, "scythe/normal"), 1);
        inventory.addItem(new Shear(AtlasAsset.Tools, "shear/normal"), 1);
        inventory.addItem(new WateringCan(AtlasAsset.Tools, "watering_can/normal"), 1);
        inventory.addItem(ItemFactory.instance.CreateSeed(CropsType.Strawberry), 9);
        inventory.addItem(ItemFactory.instance.CreateMixSeedCrop(MixedSeedsTypes.SpringMixedSeeds), 2);
        inventory.addItem(ItemFactory.instance.CreateFertilizer(), 2);

    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        //game.getEnergyManager().reduceByUsingTool(this);
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
