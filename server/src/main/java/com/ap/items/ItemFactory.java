package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.food.Food;
import com.ap.items.mine.Mineral;
import com.ap.items.plant.*;
import com.ap.model.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemFactory {
    public static ItemFactory instance = new ItemFactory();

    private AssetService assetService;

    public Item CreateGrass() {
        return new Item(ItemNames.Grass.name());
    }

    public Item CreateFiber() {
        return new Item(ItemNames.Fiber.name(), 64, AtlasAsset.Environment, "grass/Fiber");
    }
    public Item CreateStone() {
        return new Item(ItemNames.Stone.name(), 64, AtlasAsset.Environment, "stone/regular");
    }

    public Item CreateWood() {
        return new Item(ItemNames.Wood.name(), 64, AtlasAsset.Environment, "wood/regular");
    }

    public Item CreateHay() {
        return new Item(ItemNames.Hay.name(), 64, AtlasAsset.AnimalProducts, "Hay");
    }

    public Item CreateTree() {
        return new Tree(ItemNames.Tree.name());
    }

    public Item CreateSeed(CropsType belongingCropType) {
        return new Seed(AtlasAsset.Crops, belongingCropType.name() + "_Seeds", belongingCropType);
    }

    public Item CreateMixSeedCrop(MixedSeedsTypes mixSeed) {
        var crop = mixSeed.getRandom();
        return new MixSeed(AtlasAsset.Crops,"Mixed_Flower_Seeds", crop, mixSeed);
    }

    public Item CreateFertilizer() {
        return new Fertilizer(ItemNames.Fertilizer.name(), AtlasAsset.Crops, "Basic_Fertilizer");
    }
    public Item CreateCrop(CropsType type) {
        return new Crop(AtlasAsset.Crops, type.name(), type);
    }

    public Item CreateProductOfCrop(CropsType type) {
        if(type.getEnergyProduce() == null) {
            if(type == CropsType.Tulip) {
                return new Flower(type.name(),AtlasAsset.Crops,type.getName(), type.getBaseSellPrice());
            }
            return new Item(type.name(), 64, AtlasAsset.Crops,type.getName(), type.getBaseSellPrice());
        } else {
            return new Food(type.name(), 64, AtlasAsset.Crops, type.getName(), type.getBaseSellPrice(), type.getEnergyProduce());
        }
    }
    public Item CreateMineral(Minerals type) {
        return new Mineral(AtlasAsset.Mineral, type.name(), type);
    }

    public Item CreateCrafting(Crafting crafting) {
        return new CraftingItem(crafting.name(), AtlasAsset.Crafting, crafting.name(), crafting);
    }
    public Item CreateFood(Foods type, int price) {
        return new Food(type.getName(), 64, AtlasAsset.Foods, type.name(),price, type.getEnergy());
    }
    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}
